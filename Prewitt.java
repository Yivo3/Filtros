//Filtro a aplicar operador de Prewit
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Prewitt {
    public static void main(String[] args) {
        try {
            // Cargar la imagen
            BufferedImage originalImage = ImageIO.read(new File("input.jpg"));

            // Aplicar el filtro operador de Prewitt
            BufferedImage edgeImage = applyPrewittEdgeDetection(originalImage);

            // Guardar la imagen con los bordes resaltados
            File outputfile = new File("output3.jpg");
            ImageIO.write(edgeImage, "jpg", outputfile);

            System.out.println("El filtro operador de Prewitt se ha aplicado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage applyPrewittEdgeDetection(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Máscara de Prewitt para el eje horizontal
        int[][] maskX = {
            { -1, 0, 1 },
            { -1, 0, 1 },
            { -1, 0, 1 }
        };

        // Máscara de Prewitt para el eje vertical
        int[][] maskY = {
            { -1, -1, -1 },
            { 0, 0, 0 },
            { 1, 1, 1 }
        };

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumX = 0;
                int sumY = 0;

                // Aplicar la convolución con las máscaras de Prewitt
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        Color color = new Color(image.getRGB(x + i, y + j));
                        int grayscale = color.getRed(); // Considerar solo la banda de rojo (puede ser cualquier banda)

                        sumX += grayscale * maskX[j + 1][i + 1];
                        sumY += grayscale * maskY[j + 1][i + 1];
                    }
                }

                // Calcular la magnitud del gradiente
                int magnitude = (int) Math.sqrt(sumX * sumX + sumY * sumY);

                // Asegurarse de que la magnitud esté dentro del rango válido (0 a 255)
                magnitude = Math.max(0, Math.min(255, magnitude));

                // Crear un nuevo color con la magnitud del gradiente en todas las bandas
                Color edgeColor = new Color(magnitude, magnitude, magnitude);
                edgeImage.setRGB(x, y, edgeColor.getRGB());
            }
        }

        return edgeImage;
    }
}
