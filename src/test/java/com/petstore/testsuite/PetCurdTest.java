package com.petstore.testsuite;

import com.petstore.model.PetPojo;
import com.petstore.productandstoreinfo.PetSteps;
import com.petstore.testbase.TestBase;
import com.petstore.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class PetCurdTest extends TestBase {

        static String name ="Prince"+ TestUtils.getRandomValue();
        static int id =101;
        static String status = "available";
        static int petId;
        @Steps
        PetSteps petSteps;
        @Title("This will create new pet")
        @Test
        public void test001() {
            PetPojo petpojo= new PetPojo();
            HashMap<String,Object>pet = new HashMap<String, Object>();
            pet.put("id",10);
            pet.put("name","Pluto");
            List<String> photo=new ArrayList<String>();
            photo.add("ABCD");
            List<HashMap<String,Object>>petList = new ArrayList<HashMap<String,Object>>();
            petList.add(pet);
            petpojo.setId(id);
            petpojo.setCategory(pet);
            petpojo.setName(name);
            petpojo.setPhotourl(photo);
            petpojo.setTags(petList);

            ValidatableResponse response = petSteps.createUser(id,pet,name,photo,petList,status);
            response.log().all().statusCode(200);
            petId = response.log().all().extract().path("id");
            System.out.println("PET ID"+petId);
        }
    @Title("This test will Update the pet information")
    @Test
    public void test002() {
        name = name + "_updated";
        PetPojo petPojo= new PetPojo();
        HashMap<String,Object>pet = new HashMap<String, Object>();
        pet.put("id",10);
        pet.put("name","Pluto");
        List<String> photo=new ArrayList<String>();
        photo.add("ABCD");
        List<HashMap<String,Object>>petList = new ArrayList<HashMap<String,Object>>();
        petList.add(pet);
        petSteps.updateProduct(id,pet,name,photo,petList,status).statusCode(200).log().all();
        HashMap<String,Object> productMapData =petSteps.getProductInfoById(petId);
        Assert.assertThat(productMapData,hasValue(name));
    }
    @Title("Delete the pet and verify if the user is deleted!")
    @Test
    public void test003() {
        System.out.println("Id which wanted to delete"+petId);
        petSteps.deleteProduct(petId).statusCode(200);
        petSteps.getProductId(petId).statusCode(404);

    }
    }


