package lukaszzielinski;

import org.junit.jupiter.api.Test;

public class FirstTest   {

    @Test
    void myFirstTest(){
        assert true == true;
    }
    @Test
    void mySecondTest(){
        String name = "lukasz";
        String hello = String.format("Hello " + name);
        assert hello.equals("Hello lukasz");
    }

    @Test
    void baseTestSchema(){
        //Arrange  //  Given  //  Input
        //Act      //  When   //  call/interaction
        //Assert   //  Then   //  Output
    }
}
