#include "Camera.hpp"

namespace gps {

	//Camera constructor
	Camera::Camera(glm::vec3 cameraPosition, glm::vec3 cameraTarget, glm::vec3 cameraUp) {
		this->cameraPosition = cameraPosition;
		this->cameraTarget = cameraTarget;
		this->cameraUpDirection = cameraUp;

		//TODO - Update the rest of camera parameters
		this->cameraFrontDirection = glm::normalize(cameraTarget - cameraPosition);
		this->cameraRightDirection = glm::normalize(glm::cross(cameraFrontDirection, cameraUpDirection));
		this->cameraUpDirection = glm::normalize(glm::cross(cameraRightDirection, cameraFrontDirection));
	}

	//return the view matrix, using the glm::lookAt() function
	glm::mat4 Camera::getViewMatrix() {
		return glm::lookAt(cameraPosition, cameraTarget, cameraUpDirection);
	}

	//update the camera internal parameters following a camera move event
	void Camera::move(MOVE_DIRECTION direction, float speed) {
		//TODO
		switch (direction)
		{
		case gps::MOVE_FORWARD:
			if ( cameraPosition.y < 2.8f && cameraPosition.y>1.8 && cameraPosition.x < -2.3f && cameraPosition.x >-4.5f && cameraPosition.z < 2.0f && cameraPosition.z >-2.0f) {
				this->cameraPosition -= speed * cameraFrontDirection;
			}
			else {
				this->cameraPosition += speed * cameraFrontDirection;
			}
			break;

		case gps::MOVE_BACKWARD:
			this->cameraPosition -= speed * cameraFrontDirection;
			break;
		case gps::MOVE_RIGHT:
			if ((cameraPosition.y < 2.8f && cameraPosition.y>1.8 && cameraPosition.x < -2.4f && cameraPosition.x >-4.5f && cameraPosition.z < 2.0f && cameraPosition.z >-1.0f)) {
				this->cameraPosition -= speed * cameraRightDirection;
			}
			else {
				this->cameraPosition += speed * cameraRightDirection;
			}
			break;
		case gps::MOVE_LEFT:
			if ((cameraPosition.y < 2.8f && cameraPosition.y>1.8 && cameraPosition.x < -2.4f && cameraPosition.x >-4.5f && cameraPosition.z < 2.0f && cameraPosition.z >-2.0f)) {
				this->cameraPosition += speed * cameraRightDirection;
			}
			else {
				this->cameraPosition -= speed * cameraRightDirection;
			}
			break;
		default:
			break;
		}
		this->cameraTarget = cameraPosition + cameraFrontDirection;
	}

	//update the camera internal parameters following a camera rotate event
	//yaw - camera rotation around the y axis
	//pitch - camera rotation around the x axis
	void Camera::rotate(float pitch, float yaw) {
		//TODO
		glm::mat4 rot(1.0f);
		//rotate around y
		rot = glm::rotate(rot, glm::radians(pitch), cameraRightDirection);
		rot = glm::rotate(rot, glm::radians(yaw), cameraUpDirection);
		//update 
		cameraFrontDirection = rot * glm::vec4(cameraFrontDirection, 1.0f);
		cameraRightDirection = glm::normalize(glm::cross(cameraFrontDirection, cameraUpDirection));
		cameraTarget = cameraPosition + cameraFrontDirection;

		cameraUpDirection = glm::normalize(glm::cross(cameraRightDirection, cameraFrontDirection));
	}
	void Camera::rotateMouse(float pitch, float yaw)
	{
		cameraFrontDirection.x = cos(glm::radians(pitch)) * cos(glm::radians(yaw));
		cameraFrontDirection.y = sin(glm::radians(pitch));
		cameraFrontDirection.z = cos(glm::radians(pitch)) * sin(glm::radians(yaw));
		cameraTarget = cameraPosition + cameraFrontDirection;
		cameraRightDirection = glm::normalize(glm::cross(this->cameraFrontDirection, glm::vec3(0.0f, 1.0f, 0.0f)));
		cameraUpDirection = glm::normalize(glm::cross(cameraRightDirection, cameraFrontDirection));
	}
}