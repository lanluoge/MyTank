package test;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {
    @Test
    public void test(){
        try{
            BufferedImage image=  ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            assertNotNull(image);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
