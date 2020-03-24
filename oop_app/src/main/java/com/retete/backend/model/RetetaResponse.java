package com.retete.backend.model;
import java.util.ArrayList;
import java.util.List;

public class RetetaResponse {
    private List<RetetaCompleta> retete = new ArrayList<>();

    public List<RetetaCompleta> getRetete() {
        return retete;
    }

    public void setRetete(List<RetetaCompleta> retete) {
        this.retete = retete;
    }
    public RetetaResponse(Reteta reteta){
        retete.add(new RetetaCompleta(reteta));
    }
    public RetetaResponse(List<Reteta> lista){
        for(Reteta reteta:lista){
           retete.add(new RetetaCompleta(reteta));
        }
    }
}
