//Filtro a implementar Ecualización uniforme
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EcUniforme {
    public static void main(String[] args) {
        try {
            // Cargar la imagen de entrada
            BufferedImage inputImage = ImageIO.read(new File("input.jpg"));

            // Aplicar ecualización de histograma
            BufferedImage outputImage = equalizeHistogram(inputImage);

            // Guardar la imagen resultante
            File outputFile = new File("output.jpg");
            ImageIO.write(outputImage, "jpg", outputFile);

            System.out.println("Ecualizacion de histograma completada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage equalizeHistogram(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Calcular el histograma de la imagen
        int[] histogram = new int[256];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int intensity = color.getRed();
                histogram[intensity]++;
            }
        }

        // Calcular la función de ecualización acumulativa
        int[] cumulativeHistogram = new int[256];
        cumulativeHistogram[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
        }

        // Normalizar la función de ecualización acumulativa
        int totalPixels = width * height;
        float scaleFactor = 255.0f / totalPixels;
        int[] normalizedHistogram = new int[256];
        for (int i = 0; i < 256; i++) {
            normalizedHistogram[i] = Math.round(cumulativeHistogram[i] * scaleFactor);
        }

        // Crear una nueva imagen ecualizada
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int intensity = color.getRed();
                int newIntensity = normalizedHistogram[intensity];
                Color newColor = new Color(newIntensity, newIntensity, newIntensity);
                outputImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImage;
    }
}