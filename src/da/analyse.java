package da;

import java.io.*;
import java.util.*;

// Class representing a celestial body with basic properties
class CelestialBody {
    private String name;
    private double mass; // in kilograms
    private double diameter; // in kilometers

    public CelestialBody(String name, double mass, double diameter) {
        this.name = name;
        this.mass = mass;
        this.diameter = diameter;
    }

    // Getters for the properties
    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public String toString() {
        return "CelestialBody{" +
                "name='" + name + '\'' +
                ", mass=" + mass +
                ", diameter=" + diameter +
                '}';
    }
}

// Main class for analyzing celestial bodies' data
public class analyse {

    // Method for reading celestial body data from a file
    public static List<CelestialBody> readDataFromFile(String filePath) throws FileNotFoundException {
        List<CelestialBody> bodies = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filePath))) {
            // Skip the header row
            if (sc.hasNextLine()) {
                sc.nextLine(); // Skip the first line (header)
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");

                if (data.length == 3) {
                    String name = data[0];

                    try {
                        double mass = Double.parseDouble(data[1].trim());  // Parse mass as a number
                        double diameter = Double.parseDouble(data[2].trim());  // Parse diameter as a number
                        bodies.add(new CelestialBody(name, mass, diameter));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid data: " + line);
                    }
                }
            }
        }
        return bodies;
    }

    // Method for finding the celestial body with the largest mass
    public static CelestialBody findLargestMassBody(List<CelestialBody> bodies) {
        return bodies.stream()
                .max(Comparator.comparingDouble(CelestialBody::getMass))
                .orElse(null);
    }

    // Method for calculating average diameter
    public static double calculateAverageDiameter(List<CelestialBody> bodies) {
        return bodies.stream()
                .mapToDouble(CelestialBody::getDiameter)
                .average()
                .orElse(0.0);
    }

    public static void main(String[] args) {
        try {
            // Replace with the path to your data file
            String filePath = "C:\\Users\\Pankaj\\Downloads\\solar_system_data.csv";


            // Reading celestial body data
            List<CelestialBody> celestialBodies = readDataFromFile(filePath);

            // Find the celestial body with the largest mass
            CelestialBody largestMassBody = findLargestMassBody(celestialBodies);
            if (largestMassBody != null) {
                System.out.println("The celestial body with the largest mass is: " + largestMassBody);
            } else {
                System.out.println("Couldn't determine the celestial body with the largest mass.");
            }

            // Calculate and display average diameter
            double avgDiameter = calculateAverageDiameter(celestialBodies);
            System.out.println("The average diameter of celestial bodies is: " + avgDiameter);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}

