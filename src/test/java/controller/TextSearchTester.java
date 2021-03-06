package controller;

import org.junit.jupiter.api.Test;
import utils.textSearch.PlaceLocation;
import utils.textSearch.TextAnalysis;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TextSearchTester {

  @Test
  void generalSearchWorks() throws IOException {
    TextAnalysis testingTestAnalysis = new TextAnalysis();
    String input1 = "MUSO", desiredInput1= "MUSEO";
    String input2 = "Centro cult", desiredInput2 = "CENTRO CULTURAL";
    String input3 = "Calecita", desiredInput3 = "CALESITA";
    String input4 = "vivlioteca", desiredInput4 = "BIBLIOTECA";
    String input5 = "institutp", desiredInput5 = "INSTITUTO";
    String input6 = "fundasion", desiredInput6 = "FUNDACION";
    String input7 = "vlub", desiredInput7 = "CLUB";
    String input8 = ";ibreria", desiredInput8 = "LIBRERIA";
    String input9 = "parqe", desiredInput9 = "PARQUE";
    String input10 = "teattro", desiredInput10 = "TEATRO";

    for (PlaceLocation place1 : testingTestAnalysis.getSimilaritiesList(input1,10)){
      assertEquals(place1.getName().substring(0,desiredInput1.length()), desiredInput1);
    }
    for (PlaceLocation place2 : testingTestAnalysis.getSimilaritiesList(input2,10)){
      assertEquals(place2.getName().substring(0, desiredInput2.length()),desiredInput2);
    }
    for (PlaceLocation place3 : testingTestAnalysis.getSimilaritiesList(input3,10)){
      assertEquals(place3.getName().substring(0, desiredInput3.length()),desiredInput3);
    }
    for (PlaceLocation place4 : testingTestAnalysis.getSimilaritiesList(input4,10)){
      assertTrue(place4.getName().contains(desiredInput4));
    }
    for (PlaceLocation place5 : testingTestAnalysis.getSimilaritiesList(input5,10)){
      assertEquals(place5.getName().substring(0, desiredInput5.length()),desiredInput5);
    }
    for (PlaceLocation place6 : testingTestAnalysis.getSimilaritiesList(input6,10)){
      assertTrue(place6.getName().contains(desiredInput6));
    }
    for (PlaceLocation place7 : testingTestAnalysis.getSimilaritiesList(input7,10)){
      assertTrue(place7.getName().contains(desiredInput7));
    }
    for (PlaceLocation place8 : testingTestAnalysis.getSimilaritiesList(input8,10)){
      assertTrue(place8.getName().contains(desiredInput8));
    }
    for (PlaceLocation place9 : testingTestAnalysis.getSimilaritiesList(input9,10)){
      assertTrue(place9.getName().contains(desiredInput9));
    }
    for (PlaceLocation place10 : testingTestAnalysis.getSimilaritiesList(input10,10)){
      assertTrue(place10.getName().contains(desiredInput10));
    }
  }

  @Test
  void specificSearchWorks() throws IOException {
    TextAnalysis testingTestAnalysis = new TextAnalysis();

    String input1 = "liverpol", desiredInput1= "LIVERPOOL";
    String input2 = "cine palero", desiredInput2 = "CINEMARK PALERMO";
    String input3 = "plaza dorrrego", desiredInput3 = "PLAZA DORREGO";
    String input4 = "plasa balacarse", desiredInput4 = "CALESITA DE LA PLAZA BALCARCE";
    String input5 = "luna parque", desiredInput5 = "LUNA PARK";
    String input6 = "centro cultural kirner", desiredInput6 = "CENTRO CULTURAL KIRCHNER - CCK";
    String input7 = "intstituto goethe", desiredInput7 = "BIBLIOTECA GOETHE INSTITUT BUENOS AIRES";
    String input8 = "uba filo y letras", desiredInput8 = "FACULTAD DE FILOSOFIA Y LETRAS - UBA";
    String input9 = "asosiasion dante aliguieri", desiredInput9 = "ASOCIACION DANTE ALIGHIERI";
    String input10 = "museo bque coreta uruguay", desiredInput10 = "BUQUE MUSEO ARA CORBETA URUGUAY";
    int ID1 = 1388, ID2 = 2432, ID3 = 169, ID4 = 290, ID5 = 2360, ID6 = 391, ID7 = 2522, ID8 = 1072, ID9 = 314, ID10 = 924;
    assertTrue(testingTestAnalysis.getSimilaritiesList(input1,10).contains(new PlaceLocation(desiredInput1,ID1)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input2,10).contains(new PlaceLocation(desiredInput2,ID2)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input3,10).contains(new PlaceLocation(desiredInput3,ID3)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input4,10).contains(new PlaceLocation(desiredInput4,ID4)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input5,10).contains(new PlaceLocation(desiredInput5,ID5)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input6,10).contains(new PlaceLocation(desiredInput6,ID6)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input7,10).contains(new PlaceLocation(desiredInput7,ID7)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input8,10).contains(new PlaceLocation(desiredInput8,ID8)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input9,10).contains(new PlaceLocation(desiredInput9,ID9)));
    assertTrue(testingTestAnalysis.getSimilaritiesList(input10,10).contains(new PlaceLocation(desiredInput10,ID10)));


  }
}