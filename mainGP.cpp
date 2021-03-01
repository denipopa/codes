#define GLEW_STATIC
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp> //core glm functionality
#include <glm/gtc/matrix_transform.hpp> //glm extension for generating common transformation matrices
#include <glm/gtc/matrix_inverse.hpp> //glm extension for computing inverse matrices
#include <glm/gtc/type_ptr.hpp> //glm extension for accessing the internal data structure of glm types

#include "Window.h"
#include "Shader.hpp"
#include "Camera.hpp"
#include "Model3D.hpp"
#include "SkyBox.hpp"
#include <iostream>

// window
gps::Window myWindow;
const unsigned int SHADOW_WIDTH = 2048;
const unsigned int SHADOW_HEIGHT = 2048;

// matrices
glm::mat4 model;
glm::mat4 view;
glm::mat4 projection;
glm::mat3 normalMatrix;
glm::mat4 lightRotation;
// light parameters
glm::vec3 lightDir;
glm::vec3 lightColor;
glm::vec3 lightColor2;
glm::vec3 startPoint = { -4.0f, 0.0f,0.0f };
glm::vec3  controlPoint1 = { -2.0f, 3.0f, 2.0f };				// This is the first control point of the curve
glm::vec3 controlPoint2 = { 2.0f, -3.0f, -2.0f };				// This is the second control point of the curve
glm::vec3  endPoint = { 4.0f,  0.0f,  0.0f };
// shader uniform locations
GLuint modelLoc;
GLuint viewLoc;
GLuint projectionLoc;
GLuint normalMatrixLoc;
GLuint lightDirLoc;
GLuint lightColorLoc;
glm::vec3  lightSource1;

GLuint lightColorLoc1;
GLuint lightSourceLoc1;
// camera
//gps::Camera myCamera(
  //  glm::vec3(0.0f, 0.0f, 3.0f),
    //glm::vec3(0.0f, 0.0f, -10.0f),
    //glm::vec3(0.0f, 1.0f, 0.0f));
gps::Camera myCamera(
    glm::vec3(0.0f, 6.0f, -8.0f),//position
    glm::vec3(0.0f, 0.0f, 0.0f),//target
    glm::vec3(0.0f, 4.0f, 0.0f));//up

GLfloat cameraSpeed = 0.1f;

GLboolean pressedKeys[1024];

// models

gps::Model3D beach;
gps::Model3D boat;
gps::Model3D ball;
gps::Model3D lHouse;
gps::Model3D lightCube;
gps::Model3D helicopter;
gps::Model3D screenQuad;
gps::Model3D elice; 
gps::Model3D corpA;
GLfloat angle;

GLfloat lightAngle;

// shaders
gps::Shader myBasicShader;
gps::Shader lightShader;
gps::SkyBox mySkyBox;
gps::Shader skyboxShader;
gps::Shader screenQuadShader;
gps::Shader depthMapShader;

GLuint shadowMapFBO;
GLuint depthMapTexture;

bool showDepthMap;
glm::vec3 vPoint = { 0.0f, 0.0f, 0.0f };
std::vector<const GLchar*> faces;
float ballAngle=0.0f;
float ballPosition = 0.0f;

float ballx = ballPosition;
float ballY = 0.3f;
float ballZ = 0.0f;
bool ballStop = false;

bool balStopLeft = false;
bool balStopRight = false;
float boatPos = -3.4f;
float boatPosZ = 0.2f;
bool depthPass;

float currentTime = 0.0f;
GLenum glCheckError_(const char* file, int line)
{
    GLenum errorCode;
    while ((errorCode = glGetError()) != GL_NO_ERROR) {
        std::string error;
        switch (errorCode) {
        case GL_INVALID_ENUM:
            error = "INVALID_ENUM";
            break;
        case GL_INVALID_VALUE:
            error = "INVALID_VALUE";
            break;
        case GL_INVALID_OPERATION:
            error = "INVALID_OPERATION";
            break;
        case GL_STACK_OVERFLOW:
            error = "STACK_OVERFLOW";
            break;
        case GL_STACK_UNDERFLOW:
            error = "STACK_UNDERFLOW";
            break;
        case GL_OUT_OF_MEMORY:
            error = "OUT_OF_MEMORY";
            break;
        case GL_INVALID_FRAMEBUFFER_OPERATION:
            error = "INVALID_FRAMEBUFFER_OPERATION";
            break;
        }
     //   std::cout << error << " | " << file << " (" << line << ")" << std::endl;
    }
    return errorCode;
}
#define glCheckError() glCheckError_(__FILE__, __LINE__)

glm::vec3 PointOnCurve(glm::vec3 p1, glm::vec3 p2, glm::vec3 p3, glm::vec3 p4, float t)
{
    float var1, var2, var3;
    glm::vec3 vPoint = { 0.0f, 0.0f, 0.0f };

    
    var1 = 1 - t;

    var2 = var1 * var1 * var1;

    var3 = t * t * t;

   
    vPoint.x = var2 * p1.x + 3 * t * var1 * var1 * p2.x + 3 * t * t * var1 * p3.x + var3 * p4.x;
    vPoint.y = var2 * p1.y + 3 * t * var1 * var1 * p2.y + 3 * t * t * var1 * p3.y + var3 * p4.y;
    vPoint.z = var2 * p1.z + 3 * t * var1 * var1 * p2.z + 3 * t * t * var1 * p3.z + var3 * p4.z;

    // Now we should have the point on the curve, so let's return it.
    return vPoint;
}

void windowResizeCallback(GLFWwindow* window, int width, int height) {
    fprintf(stdout, "Window resized! New width: %d , and height: %d\n", width, height);
    //TODO
    projection = glm::perspective
    (glm::radians(45.0f),
        (float)width / (float)height,
        0.1f, 1000.0f);
    projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
    // send projection matrix to shader
    glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));
}

void keyboardCallback(GLFWwindow* window, int key, int scancode, int action, int mode) {
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(window, GL_TRUE);
    }
    if (key == GLFW_KEY_M && action == GLFW_PRESS)
        showDepthMap = !showDepthMap;

    if (key >= 0 && key < 1024) {
        if (action == GLFW_PRESS) {
            pressedKeys[key] = true;
        }
        else if (action == GLFW_RELEASE) {
            pressedKeys[key] = false;
        }
    }
}

double pitch =0.0f;
double yaw = 0.0f;
double lastX, lastY;
double xYaw = 0.0;
double yPitch = 0.0;
void mouseCallback(GLFWwindow* window, double xpos, double ypos) {
    xYaw = xpos - lastX;
    yPitch = lastY - ypos;
    lastX = xpos;
    lastY = ypos;
    xYaw = xYaw / 10.0;
    yPitch = yPitch / 10.0;
    yaw = yaw + xYaw;
    pitch = pitch + yPitch;
    if (pitch > 89.0) {
        pitch = 89.0;
    }
    if (pitch < -89.0) {
        pitch = -89.0;
    }

    myCamera.rotateMouse(pitch, yaw);
    //view = myCamera.getViewMatrix();
    //myBasicShader.useShaderProgram();
   // glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
    
}

void processMovement() {
    
   // glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
    if (glfwGetKey(myWindow.getWindow(), GLFW_KEY_1)) {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }
    if (glfwGetKey(myWindow.getWindow(), GLFW_KEY_2)) {

        glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
    }
    if (glfwGetKey(myWindow.getWindow(), GLFW_KEY_3)) {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    if (pressedKeys[GLFW_KEY_W]) {
        myCamera.move(gps::MOVE_FORWARD, cameraSpeed);

    }

    if (pressedKeys[GLFW_KEY_S]) {
        myCamera.move(gps::MOVE_BACKWARD, cameraSpeed);
       

    }

    if (pressedKeys[GLFW_KEY_A]) {
        myCamera.move(gps::MOVE_LEFT, cameraSpeed);

    }

    if (pressedKeys[GLFW_KEY_D]) {
        myCamera.move(gps::MOVE_RIGHT, cameraSpeed);

    }

    if (pressedKeys[GLFW_KEY_Q]) {
        angle -= 1.0f;
        // update model matrix for teapot
        model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0, 1, 0));
        // update normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    }

    if (pressedKeys[GLFW_KEY_E]) {
        angle += 1.0f;
        // update model matrix for teapot
        model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0, 1, 0));
        // update normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    }
    if (pressedKeys[GLFW_KEY_J]) {
      //  lightAngle -= 1.0f;
        lightAngle += 1.0f;
        if (lightAngle > 360.0f)
            lightAngle -= 360.0f;
        glm::vec3 lightDirTr = glm::vec3(glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f)) * glm::vec4(lightDir, 1.0f));
        myBasicShader.useShaderProgram();
        glUniform3fv(lightDirLoc, 1, glm::value_ptr(lightDirTr));
    }

    if (pressedKeys[GLFW_KEY_L]) {
        //lightAngle += 1.0f;
        lightAngle -= 1.0f;
        if (lightAngle < 0.0f)
            lightAngle += 360.0f;
        glm::vec3 lightDirTr = glm::vec3(glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f)) * glm::vec4(lightDir, 1.0f));
        myBasicShader.useShaderProgram();
        glUniform3fv(lightDirLoc, 1, glm::value_ptr(lightDirTr));
    }
    if (pressedKeys[GLFW_KEY_LEFT]) {
        myCamera.rotate(0.0f, 1.0f);
          
    }

    if (pressedKeys[GLFW_KEY_RIGHT]) {
        myCamera.rotate(0.0f, -1.0f);
            
    }

    if (pressedKeys[GLFW_KEY_UP]) {
        myCamera.rotate(1.0f, 0.0f);
       
    }

    if (pressedKeys[GLFW_KEY_DOWN]) {
        myCamera.rotate(-1.0f, 0.0f);
           
    }
    if (pressedKeys[GLFW_KEY_B]) {
        ballAngle += 1.0f;
        if (ballAngle > 360.0f) {
            ballAngle -= 360.0f;
        }
    }
    if(pressedKeys[GLFW_KEY_N])   
    {
        //for (float t = 0; t <= (1 + (1.0f / 100)); t += 1.0f / 100)
    //{
        //glm::vec3 vPoint = { 0.0f, 0.0f, 0.0f };
        // Get the current point on the curve, depending on the time.
        vPoint = PointOnCurve(startPoint, controlPoint1, controlPoint2, endPoint, currentTime);
        gps::Camera myCamera(glm::vec3(vPoint.x, vPoint.y, vPoint.z),//position
            glm::vec3(0.0f, 0.0f, 0.0f),//target
            glm::vec3(0.0f, 5.0f, 0.0f));
        // Draw the current point at distance "t" of the curve.
        glVertex3f(vPoint.x, vPoint.y, vPoint.z);
        //myCamera.move(gps::MOVE_FORWARD, cameraSpeed);
        //view = myCamera.getViewMatrix();
       // viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
        //glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

       // myBasicShader.useShaderProgram();
        //glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
       // normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        //normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");

       // vPoint = PointOnCurve(startPoint, controlPoint1, controlPoint2, endPoint, t);
   // }
    }
    if (pressedKeys[GLFW_KEY_C]) {
        if (balStopLeft == false) {
            balStopRight = true;
            boatPosZ -= 0.01f;

        }
        else {
            // balStopRight = false;
            boatPosZ += 0.01f;

        }

        if (balStopRight == false) {
            //balStopLeft = true;
            boatPosZ += 0.01f;

        }
        else {
            // balStopLeft = false;
            boatPosZ -= 0.01f;

        }
    }
}

void initOpenGLWindow() {
    myWindow.Create(1920, 1080, "OpenGL Project Core");
}

void setWindowCallbacks() {
    glfwSetWindowSizeCallback(myWindow.getWindow(), windowResizeCallback);
    glfwSetKeyCallback(myWindow.getWindow(), keyboardCallback);
    glfwSetCursorPosCallback(myWindow.getWindow(), mouseCallback);
}

void initOpenGLState() {
    glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
    glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
    glEnable(GL_FRAMEBUFFER_SRGB);
    glEnable(GL_DEPTH_TEST); // enable depth-testing
    glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
    glEnable(GL_CULL_FACE); // cull face
    glCullFace(GL_BACK); // cull back face
    glFrontFace(GL_CCW); // GL_CCW for counter clock-wise
}

void initModels() {
    beach.LoadModel("models/BEACH/beach.obj");
    boat.LoadModel("models/boat/boat.obj");
    ball.LoadModel("models/ball/ball2.obj");
    lHouse.LoadModel("models/lighthouse/felinar.obj");
    helicopter.LoadModel("models/helicopter/helicopter.obj");
    lightCube.LoadModel("objects/cube/cube.obj");
    screenQuad.LoadModel("objects/quad/quad.obj");
    corpA.LoadModel("models/avion/corpA.obj");
    elice.LoadModel("models/avion/elice.obj");
    
    faces.push_back("skybox/day-at-the-beach_bk.tga");
    faces.push_back("skybox/day-at-the-beach_dn.tga");
    faces.push_back("skybox/day-at-the-beach_ft.tga");
    faces.push_back("skybox/day-at-the-beach_lf.tga");
    faces.push_back("skybox/day-at-the-beach_rt.tga");
    faces.push_back("skybox/day-at-the-beach_up.tga");
 
}

void initShaders() {
    myBasicShader.loadShader(
        "shaders/shaderStart.vert",
        "shaders/shaderStart.frag");
    myBasicShader.useShaderProgram();
    depthMapShader.loadShader("shaders/depthMapShader.vert", "shaders/depthMapShader.frag");
    depthMapShader.useShaderProgram();
    lightShader.loadShader("shaders/lightCube.vert", "shaders/lightCube.frag");
    lightShader.useShaderProgram();
    screenQuadShader.loadShader("shaders/screenQuad.vert", "shaders/screenQuad.frag");
    screenQuadShader.useShaderProgram();
}

void initUniforms() {
    myBasicShader.useShaderProgram();

    // create model matrix for teapot
    model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0.0f, 1.0f, 0.0f));
    modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");
 
    view = myCamera.getViewMatrix();
    viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
    // send view matrix to shader
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

    // compute normal matrix for teapot
    normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");

    // create projection matrix
    projection = glm::perspective(glm::radians(45.0f),
        (float)myWindow.getWindowDimensions().width / (float)myWindow.getWindowDimensions().height, 0.1f, 60.0f);
    projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
    // send projection matrix to shader
    glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));

    //set the light direction (direction towards the light)
    lightDir = glm::vec3(0.0f, 1.0f, 1.0f);
    lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f));
    lightDirLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDir");
    // send light dir to shader
    glUniform3fv(lightDirLoc, 1, glm::value_ptr(lightDir));

    //set light color
    lightColor = glm::vec3(1.0f, 1.0f, 1.0f); //white light
    lightColorLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor");
    // send light color to shader
    glUniform3fv(lightColorLoc, 1, glm::value_ptr(lightColor));

    lightColor2 = glm::vec3(0.35f, 0.0f, 1.0f);
    lightColorLoc1 = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor1");
    glUniform3fv(lightColorLoc1, 1, glm::value_ptr(lightColor2));
    lightSource1 = glm::vec3(1.0f, 1.0f, 1.0f);
    lightSourceLoc1 = glGetUniformLocation(myBasicShader.shaderProgram, "lightSource1");

    glUniformMatrix3fv(lightSourceLoc1, 1, GL_FALSE, glm::value_ptr(lightSource1));
    lightShader.useShaderProgram();
    glUniformMatrix4fv(glGetUniformLocation(lightShader.shaderProgram, "projection"), 1, GL_FALSE, glm::value_ptr(projection));

    
    mySkyBox.Load(faces);


    skyboxShader.loadShader("shaders/skyboxShader.vert", "shaders/skyboxShader.frag");
    skyboxShader.useShaderProgram();

    
}

void initFBO() {
    //TODO - Create the FBO, the depth texture and attach the depth texture to the FBO
    //generate FBO ID
    glGenFramebuffers(1, &shadowMapFBO);
    //create depth texture for FBO
    glGenTextures(1, &depthMapTexture);
    glBindTexture(GL_TEXTURE_2D, depthMapTexture);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT,
        SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
    //attach texture to FBO
    glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMapTexture, 0);

    glDrawBuffer(GL_NONE);
    glReadBuffer(GL_NONE);
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    depthMapShader.useShaderProgram();

}
glm::mat4 computeLightSpaceTrMatrix() {
    //TODO - Return the light-space transformation matrix
    glm::mat4 lightView = glm::lookAt(lightDir, glm::vec3(0.0f), glm::vec3(0.0f, 1.0f, 0.0f));
    const GLfloat near_plane = -10.1f, far_plane = 10.0f;
    glm::mat4 lightProjection = glm::ortho(-10.0f, 10.0f, -10.0f, 10.0f, near_plane, far_plane);

    glm::mat4 lightSpaceTrMatrix = lightProjection * lightView;

    return lightSpaceTrMatrix;

}
float boatScale = 0.4f;
float boatAngle = 0.0f;
glm::mat4 definePosition(float x, float y, float z, float scaleX = 1.0f, float scaleY = 1.0f, float scaleZ = 1.0f)
{
    model = glm::translate(glm::mat4(1.0f), glm::vec3(x, y, z));
    model = glm::scale(model, glm::vec3(scaleX, scaleY, scaleZ));
    return model;
}
glm::mat4 definePositionLight(float x, float y, float z, float scaleX = 1.0f, float scaleY = 1.0f, float scaleZ = 1.0f)
{
    model = glm::translate(glm::mat4(1.0f), glm::vec3(x, y, z));
    model = glm::rotate(model, glm::radians(angle), glm::vec3(0.0f, 1.0f, 0.0f));
    model = glm::scale(model, glm::vec3(scaleX, scaleY, scaleZ));
    return model;
}
glm::mat4 definePositionBoat(float x, float y, float z, float scaleX = 1.0f, float scaleY = 1.0f, float scaleZ = 1.0f)
{   
     model = glm::translate(glm::mat4(1.0f), glm::vec3(x, y, z));
     model = glm::scale(model, glm::vec3(scaleX, scaleY, scaleZ));
     return model;
}

void renderBall(gps::Shader shader,bool depthPass) {

    if (ballPosition > 0.75f  )
    {
        balStopRight = true;
        balStopLeft = false;
    }
    if ( ballPosition < -0.75f)
    {
        balStopLeft= true;
        balStopRight = false;
    }

    if (balStopLeft==false) {
       balStopRight = true;
            ballPosition -= 0.001f;
        
    }
    else {
            ballPosition += 0.001f;
      
    }

    if (balStopRight == false) {
        ballPosition += 0.001f;

    }
    else {
        ballPosition -= 0.001f;

    }
     shader.useShaderProgram();
  
    model = definePosition(ballPosition, 0.3f, 0.0f, 0.20f, 0.20f, 0.20f);

    model = glm::rotate(model, glm::radians(ballAngle), glm::vec3(0, 0, 1));
    model = glm::rotate(model, glm::radians(ballAngle), glm::vec3(1, 0, 0));

    glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
    
    if (!depthPass) {
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }

    ball.Draw(shader);
}
float rotateAngle3 = 0.1f;
float airPosition = 6.0f;
float airPositionZ = -13.0f;
double lastTimeStamp = glfwGetTime();
void renderHelicopter(gps::Shader shader) {
  
    airPosition += 0.007f;
    airPositionZ += 0.001f;
    
    model = glm::translate(glm::mat4(1.0f), glm::vec3(airPosition, 5.0f, airPositionZ));
    model = glm::scale(model, glm::vec3(0.7f, 0.7f, 0.7f));
    model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0, 1, 0));

    shader.useShaderProgram();
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));
    glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    helicopter.Draw(shader);
}

bool rotate = false;
float rotateAngle2 = 90.0f;
void renderHelicopter2(gps::Shader shader,bool depthPass) {
    
   shader.useShaderProgram();
   
   model = glm::translate(glm::mat4(1.0f), glm::vec3(-3.4f, 2.1f, 0.0f));
   //model = glm::translate(glm::mat4(1.0f), glm::vec3((-3.4f + (sin(rotateAngle2) * 2.0f)), 2.1f, (-3.4f + (cos(rotateAngle2) * 2.0f))));
   model = glm::rotate(model, glm::radians(-rotateAngle2), glm::vec3(0, 1, 0));
   rotateAngle2 = rotateAngle2 + 0.01f;
   model = glm::scale(model, glm::vec3(1.0f, 1.0f, 1.0f));
    
   glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
 
   if (!depthPass) {
       normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
       glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
   }
    corpA.Draw(shader);
}

void renderHelicopter3(gps::Shader shader, bool depthPass) {

        shader.useShaderProgram();

        model = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 2.0f, 0.0f));
        model = glm::translate(glm::mat4(1.0f), glm::vec3((3.4f + (sin(rotateAngle3) * 2.0f)), 2.0f, (3.4f + (cos(rotateAngle3) * 2.0f))));
        rotateAngle3 = rotateAngle3 + 0.01f;
        model = glm::scale(model, glm::vec3(0.2f, 0.2f, 0.2f));

        glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
        
        if (!depthPass) {
            normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
            glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
        }
        
        helicopter.Draw(shader);
 }
float rotateElice = 90.0f;

void renderElice2(gps::Shader shader) {
    //float rotateAngle = delta / 0.5;
   
    airPosition += 0.001f;
    airPositionZ += 0.001f;
   
      model = glm::translate(glm::mat4(1.0f), glm::vec3(-3.4f, 2.35f, 0.0f));
    
      model = glm::scale(model, glm::vec3(0.5f, 0.5f, 0.5f));
      model = glm::rotate(model, glm::radians(rotateAngle2), glm::vec3(0, 1, 0));
      rotateAngle2 = rotateAngle2 + 1.0f;
      
    shader.useShaderProgram();
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));
    glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    elice.Draw(shader);
}
void renderCanoe(gps::Shader shader ,bool depthPass) {
    model = definePositionBoat(boatPos, 0.0f, boatPosZ, 0.25f, 0.25f, 0.25f);
   
    if (boatPosZ> 3.6f )
    {
        balStopRight = true;
        balStopLeft = false;
    }
    if (boatPosZ < -6.0f)
    {
        balStopLeft = true;
        balStopRight = false;
    }


    shader.useShaderProgram();
    
    
    glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
    
    if (!depthPass) {
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }
   
   
     boat.Draw(shader);

    
}
void renderBeach(gps::Shader shader, bool depthPass) {
    // select active shader program
    shader.useShaderProgram();
    model = definePosition(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f);
    

    glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
    
    if (!depthPass) {
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }

    
    beach.Draw(shader);
}

void positionalLight() {
    glm::mat4 myModel = myCamera.getViewMatrix();
    myModel = glm::translate(myModel, glm::vec3(-8.4f, 3.2f, 0.0f));
    lightSource1 = myModel * glm::vec4(1.0f, 1.0f, 1.0f, 1.0f);
    glUniform3fv(lightSourceLoc1, 1, glm::value_ptr(lightSource1));
    glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));

}
void renderLight(gps::Shader shader,bool depthPass) {
    // select active shader program
    shader.useShaderProgram();
    model = definePositionLight(-8.4f, 0.2f, 0.0f, 0.55f, 0.55f, 0.55f);
   
    glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
    
    if (!depthPass) {
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }

    lHouse.Draw(shader);
    positionalLight();
}


void renderCube(gps::Shader shader) {
    shader.useShaderProgram();

    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

    //send teapot normal matrix data to shader
    glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));

    lightCube.Draw(lightShader);
}
void renderScene(gps::Shader shader,bool depthPass) {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    renderBeach(shader, depthPass);
    renderCanoe(shader,depthPass);
    renderBall(shader,depthPass);
    renderHelicopter(myBasicShader);
    renderHelicopter2(shader, depthPass);
    renderHelicopter3(shader, depthPass);
   // renderElice(shader, depthPass);
    renderElice2(shader);
    renderLight(shader,depthPass);
    mySkyBox.Draw(skyboxShader, view, projection);
    renderCube(lightShader);
}
void shadowRender() {
    depthMapShader.useShaderProgram();
    glUniformMatrix4fv(glGetUniformLocation(depthMapShader.shaderProgram, "lightSpaceTrMatrix"), 1, GL_FALSE, glm::value_ptr(computeLightSpaceTrMatrix()));
    glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
    glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
    glClear(GL_DEPTH_BUFFER_BIT);
    //	RenderTheScene();
    renderScene(depthMapShader,true);
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    vPoint = PointOnCurve(startPoint, controlPoint1, controlPoint2, endPoint, currentTime);

    // render depth map on screen - toggled with the M key

    if (showDepthMap) {
        glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);

        glClear(GL_COLOR_BUFFER_BIT);

        screenQuadShader.useShaderProgram();

        //bind the depth map
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, depthMapTexture);
        glUniform1i(glGetUniformLocation(screenQuadShader.shaderProgram, "depthMap"), 0);

        glDisable(GL_DEPTH_TEST);
        screenQuad.Draw(screenQuadShader);
        glEnable(GL_DEPTH_TEST);
    }
    else {

        // final scene rendering pass (with shadows)

        glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        myBasicShader.useShaderProgram();

        view = myCamera.getViewMatrix();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

        lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f));
        glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation)) * lightDir));

        //bind the shadow map
        glActiveTexture(GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, depthMapTexture);
        glUniform1i(glGetUniformLocation(myBasicShader.shaderProgram, "shadowMap"), 3);

        glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightSpaceTrMatrix"),
            1,
            GL_FALSE,
            glm::value_ptr(computeLightSpaceTrMatrix()));

        //drawObjects(myCustomShader, false);
        renderScene(myBasicShader,false);

        //draw a white cube around the light

        lightShader.useShaderProgram();

        glUniformMatrix4fv(glGetUniformLocation(lightShader.shaderProgram, "view"), 1, GL_FALSE, glm::value_ptr(view));

        model = lightRotation;
        model = glm::translate(model, 1.0f * lightDir);
        model = glm::scale(model, glm::vec3(0.05f, 0.05f, 0.05f));
        glUniformMatrix4fv(glGetUniformLocation(lightShader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));

        lightCube.Draw(lightShader);

       
    }
}


void cleanup() {
    glDeleteTextures(1, &depthMapTexture);
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    glDeleteFramebuffers(1, &shadowMapFBO);
    myWindow.Delete();
    //cleanup code for your own data
}

int main(int argc, const char* argv[]) {

    try {
        initOpenGLWindow();
    }
    catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return EXIT_FAILURE;
    }

    initOpenGLState();
    initModels();
    initShaders();
    initUniforms();
    initFBO();
    setWindowCallbacks();

    glCheckError();
    // application loop
    while (!glfwWindowShouldClose(myWindow.getWindow())) {
        processMovement();
      //  renderScene(false);
        shadowRender();

        glfwPollEvents();
        glfwSwapBuffers(myWindow.getWindow());

        glCheckError();
    }

    cleanup();

    return EXIT_SUCCESS;
}
