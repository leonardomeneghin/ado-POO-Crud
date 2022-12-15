package com.example.pooexercise.model;

public class AnimalModel {
    String idInstitution = "";
    String animalName = "";
    String animelBreed = "";
    String animalAge = "";
    String animalSize = "";
    String animalMedicine = "";
    String animalTimeMedicine = "";
    String animalObs = "";
    String urlImageDog = "";
    String idAnimal = "";


    public AnimalModel(){

    }
    public AnimalModel(String idInstitution, String idAnimal,String animalName, String animelBreed, String animalAge, String animalSize, String animalMedicine, String animalTimeMedicine, String animalObs, String urlImageDog) {
        this.idInstitution = idInstitution;
        this.animalName = animalName;
        this.animelBreed = animelBreed;
        this.animalAge = animalAge;
        this.animalSize = animalSize;
        this.animalMedicine = animalMedicine;
        this.animalTimeMedicine = animalTimeMedicine;
        this.animalObs = animalObs;
        this.urlImageDog = urlImageDog;
        this.idAnimal = idAnimal;

    }

    public String getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(String idInstitution) {
        this.idInstitution = idInstitution;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getUrlImageDog() {
        return urlImageDog;
    }

    public void setUrlImageDog(String urlImageDog) {
        this.urlImageDog = urlImageDog;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimelBreed() {
        return animelBreed;
    }

    public void setAnimelBreed(String animelBreed) {
        this.animelBreed = animelBreed;
    }

    public String getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(String animalAge) {
        this.animalAge = animalAge;
    }

    public String getAnimalSize() {
        return animalSize;
    }

    public void setAnimalSize(String animalSize) {
        this.animalSize = animalSize;
    }

    public String getAnimalMedicine() {
        return animalMedicine;
    }

    public void setAnimalMedicine(String animalMedicine) {
        this.animalMedicine = animalMedicine;
    }

    public String getAnimalTimeMedicine() {
        return animalTimeMedicine;
    }

    public void setAnimalTimeMedicine(String animalTimeMedicine) {
        this.animalTimeMedicine = animalTimeMedicine;
    }

    public String getAnimalObs() {
        return animalObs;
    }

    public void setAnimalObs(String animalObs) {
        this.animalObs = animalObs;
    }
}
