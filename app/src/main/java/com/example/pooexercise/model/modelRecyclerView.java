package com.example.pooexercise.model;

public class modelRecyclerView {
    //model da recyclerview

    String animalName, animalAge, urlImageDog, idAnimal;

    public modelRecyclerView() {

    }

    public modelRecyclerView(String idAnimal,String animalName, String animalAge, String urlImageDog) {
        this.animalName = animalName;
        this.animalAge = animalAge;
        this.urlImageDog = urlImageDog;
        this.idAnimal = idAnimal;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(String animalAge) {
        this.animalAge = animalAge;
    }

    public String getUrlImageDog() {
        return urlImageDog;
    }

    public void setUrlImageDog(String urlImageDog) {
        this.urlImageDog = urlImageDog;
    }
}
