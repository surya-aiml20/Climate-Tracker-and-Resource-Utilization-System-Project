/*

Problem Statement:

Develop a console-based system that:

1. Tracks climate data (temperature, humidity, rainfall).

2. Monitors resource utilization (water, electricity, gas).

3. Generates reports on climate trends, resource consumption, and sustainability
metrics.

4. Stores and retrieves data from a database.

//Problem Breakdown:
(REQUIREMENTS)

1. Database Design:
○ Create tables to store climate data, resource consumption data, and system
configuration settings.

2. Climate Data Collection:
○ Implement methods to collect climate data from various sources (sensors,
weather APIs).
○ Store data like temperature, humidity, rainfall, and air quality.

3. Resource Usage Tracking:
○ Implement mechanisms to track resource consumption (electricity, water,
gas).
○ Use sensors or manual input to collect data.

4. Data Analysis and Visualization:
○ Analyze historical climate data to identify trends and patterns.
○ Visualize data using charts and graphs.

5. Report Generation:
○ Generate reports on climate trends, resource consumption, and sustainability
metrics.

6. Alert and Notification System:
○ Implement a system to send alerts and notifications for extreme weather
events, resource overuse, or system anomalies.

7. Error Handling and Validation:
○ Implement error handling to catch exceptions like database errors or invalid
data.
○ Validate data input to ensure accuracy.*/

package main;
import java.sql.*;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClimateTrackerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nClimate Tracker and Resource Utilization System");
            System.out.println("1. Add Climate Data");
            System.out.println("2. Add Resource Usage Data");
            System.out.println("3. View Climate Trends");
            System.out.println("4. View Resource Consumption Report");
            System.out.println("5. Set Configuration");
            System.out.println("6. Check Alerts");
            System.out.println("7. View Historical Climate Data");
            System.out.println("8. View Resource Usage by Type");
            System.out.println("9. Generate Monthly Report");
            System.out.println("10. Add Location");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addClimateData(scanner);
                    break;
                case 2:
                    addResourceUsageData(scanner);
                    break;
                case 3:
                    viewClimateTrends(scanner);
                    break;
                case 4:
                    viewResourceConsumptionReport(scanner);
                    break;
                case 5:
                    setConfiguration(scanner);
                    break;
                case 6:
                    checkAlerts(scanner);
                    break;
                case 7:
                    viewHistoricalClimateData(scanner);
                    break;
                case 8:
                    viewResourceUsageByType(scanner);
                    break;
                case 9:
                    generateMonthlyReport(scanner);
                    break;
                case 10:
                    addLocation(scanner);
                    break;
                case 11:
                    running = false;
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
        scanner.close();
    }

    // Method to add climate data
    private static void addClimateData(Scanner scanner) {
        try {
            System.out.print("Enter location ID: ");
            int locationId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
            System.out.print("Enter location name: ");
            String locationName = scanner.nextLine();

            System.out.print("Enter temperature (°C): ");
            double temperature = scanner.nextDouble();
            System.out.print("Enter humidity (%): ");
            double humidity = scanner.nextDouble();
            System.out.print("Enter rainfall (mm): ");
            double rainfall = scanner.nextDouble();
            System.out.print("Enter air quality index: ");
            String airQuality = scanner.next();

            // Validate inputs for climate data
            if (temperature < -50 || temperature > 50 || humidity < 0 || humidity > 100 || rainfall < 0) {
                System.out.println("Invalid climate data input.");
                return;
            }

            // Connect to the database and insert data
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO ClimateData (location_id, location_name, temperature, humidity, rainfall, air_quality) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, locationId);
            stmt.setString(2, locationName);
            stmt.setDouble(3, temperature);
            stmt.setDouble(4, humidity);
            stmt.setDouble(5, rainfall);
            stmt.setString(6, airQuality);
            stmt.executeUpdate();
            System.out.println("Climate data added successfully.");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    // Method to add resource usage data
    private static void addResourceUsageData(Scanner scanner) {
        try {
            System.out.print("Enter location ID: ");
            int locationId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
            System.out.print("Enter location name: ");
            String locationName = scanner.nextLine();

            System.out.print("Enter resource type (electricity/water/gas): ");
            String resourceType = scanner.next();
            System.out.print("Enter usage amount: ");
            double usageAmount = scanner.nextDouble();

            // Validate input for usage amount
            if (usageAmount < 0) {
                System.out.println("Invalid usage amount.");
                return;
            }

            // Connect to the database and insert data
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO ResourceUsage (location_id, location_name, resource_type, usage_amount) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, locationId);
            stmt.setString(2, locationName);
            stmt.setString(3, resourceType);
            stmt.setDouble(4, usageAmount);
            stmt.executeUpdate();
            System.out.println("Resource usage data added successfully.");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    // Method to add location
    private static void addLocation(Scanner scanner) {
        try {
            scanner.nextLine();  // Consume newline character
            System.out.print("Enter location name: ");
            String locationName = scanner.nextLine();

            if (locationName.isEmpty()) {
                System.out.println("Location name cannot be empty.");
                return;
            }

            System.out.print("Enter region : ");
            String region = scanner.nextLine();

            System.out.print("Enter country : ");
            String country = scanner.nextLine();

            // If region or country is not provided, you can set them to NULL or empty strings
            if (region.isEmpty()) {
                region = null;  // You could also use "" if your database allows empty strings.
            }
            if (country.isEmpty()) {
                country = null;
            }

            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Locations (location_name, region, country) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, locationName);
            stmt.setString(2, region);
            stmt.setString(3, country);

            stmt.executeUpdate();
            System.out.println("Location added successfully.");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error adding location: " + e.getMessage());
        }
    }

    // Method to generate monthly report based on location ID
    private static void generateMonthlyReportToFile(Scanner scanner) {
        String reportDirectory = "C:\\Users\\Surya\\OneDrive\\Documents\\monthly reports";

        // Prompt the user for the month, year, location ID, and report type
        System.out.print("Enter the month (1-12) for the report: ");
        int month = scanner.nextInt();
        System.out.print("Enter the year for the report: ");
        int year = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter location ID for the report: ");
        int locationId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Do you want a climate report, resource usage report, or both? (Enter 'climate', 'resource', or 'both'): ");
        String reportType = scanner.nextLine().toLowerCase();

        // Construct the file name with the selected month, year, and location ID
        String monthYear = DateTimeFormatter.ofPattern("MMMM-yyyy").format(java.time.YearMonth.of(year, month));
        String fileName = reportDirectory + "\\Monthly_Report_Location_" + locationId + "_" + monthYear + ".txt";

        // Ensure the report directory exists
        File directory = new File(reportDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            Connection conn = DatabaseConnection.getConnection();

            if (reportType.equals("climate") || reportType.equals("both")) {
                // Write the climate report
                String climateSql = "SELECT AVG(temperature) AS avg_temp, AVG(humidity) AS avg_humidity, AVG(rainfall) AS avg_rainfall " +
                        "FROM ClimateData WHERE MONTH(timestamp) = ? AND YEAR(timestamp) = ? AND location_id = ?";
                PreparedStatement climateStmt = conn.prepareStatement(climateSql);
                climateStmt.setInt(1, month);
                climateStmt.setInt(2, year);
                climateStmt.setInt(3, locationId);
                ResultSet climateRs = climateStmt.executeQuery();

                writer.write("Monthly Climate Report:\n");
                if (climateRs.next()) {
                    writer.write(String.format("Location ID: %d | Month: %s | Avg Temperature: %.2f°C | Avg Humidity: %.2f%% | Avg Rainfall: %.2fmm\n",
                            locationId, monthYear, climateRs.getDouble("avg_temp"), climateRs.getDouble("avg_humidity"), climateRs.getDouble("avg_rainfall")));
                } else {
                    writer.write("No climate data available for the selected month, year, and location.\n");
                }
            }

            if (reportType.equals("resource") || reportType.equals("both")) {
                // Write the resource usage report
                String resourceSql = "SELECT resource_type, AVG(usage_amount) AS avg_usage FROM ResourceUsage " +
                        "WHERE MONTH(timestamp) = ? AND YEAR(timestamp) = ? AND location_id = ? GROUP BY resource_type";
                PreparedStatement resourceStmt = conn.prepareStatement(resourceSql);
                resourceStmt.setInt(1, month);
                resourceStmt.setInt(2, year);
                resourceStmt.setInt(3, locationId);
                ResultSet resourceRs = resourceStmt.executeQuery();

                writer.write("\nMonthly Resource Usage Report:\n");
                boolean hasResourceData = false;
                while (resourceRs.next()) {
                    hasResourceData = true;
                    writer.write(String.format("Resource: %s | Avg Usage: %.2f\n",
                            resourceRs.getString("resource_type"), resourceRs.getDouble("avg_usage")));
                }
                if (!hasResourceData) {
                    writer.write("No resource usage data available for the selected month, year, and location.\n");
                }
            }

            writer.close();
            System.out.println("Monthly report generated successfully at " + fileName);
            conn.close();
        } catch (SQLException | IOException e) {
            System.out.println("Error generating the monthly report: " + e.getMessage());
        }
    }

    private static void viewClimateTrends(Scanner scanner) {
        try {
            // Prompt the user to choose whether to view data for a specific location or all data
            System.out.print("Do you want to view climate trends for a specific location (Enter location ID) or all data? (Enter 'all' for all data): ");

            // Clear any leftover newline from previous input
            scanner.nextLine();  // This ensures we start with a clean input buffer

            String locationInput = scanner.nextLine();  // Read the input

            // Debugging: print input to see what is entered
            System.out.println("You entered: " + locationInput);

            // Check if the input is not empty
            if (locationInput.trim().isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number or 'all'.");
                return; // Exit if input is empty
            }

            // Open a database connection
            Connection conn = DatabaseConnection.getConnection();

            String sql;

            // If user enters 'all', fetch the average for the entire dataset
            if (locationInput.equalsIgnoreCase("all")) {
                sql = "SELECT AVG(temperature) AS avg_temp, AVG(humidity) AS avg_humidity, AVG(rainfall) AS avg_rainfall FROM ClimateData";
            } else {
                // Validate if the entered location ID is a valid integer
                int locationId;
                try {
                    locationId = Integer.parseInt(locationInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid location ID input. Please enter a valid number or 'all'.");
                    return;  // Exit the method if the input is invalid
                }

                // Check if the location ID exists in the Locations table
                String checkLocationSql = "SELECT COUNT(*) FROM Locations WHERE location_id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkLocationSql);
                checkStmt.setInt(1, locationId);
                ResultSet checkRs = checkStmt.executeQuery();

                if (checkRs.next() && checkRs.getInt(1) > 0) {
                    // If location ID is valid, fetch data for that specific location
                    sql = "SELECT AVG(temperature) AS avg_temp, AVG(humidity) AS avg_humidity, AVG(rainfall) AS avg_rainfall " +
                            "FROM ClimateData WHERE location_id = ?";
                } else {
                    System.out.println("No data found for the specified location ID.");
                    return;  // Exit the method if the location ID is invalid
                }
            }

            // Prepare the query
            PreparedStatement stmt = conn.prepareStatement(sql);

            // If location is specified, set the location ID parameter
            if (!locationInput.equalsIgnoreCase("all")) {
                stmt.setInt(1, Integer.parseInt(locationInput));  // Set location ID if not "all"
            }

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Create a dataset for the chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Check if the query returns a result
            if (rs.next()) {
                // Retrieve average values from the result set
                double avgTemp = rs.getDouble("avg_temp");
                double avgHumidity = rs.getDouble("avg_humidity");
                double avgRainfall = rs.getDouble("avg_rainfall");

                // Debugging: Print values to console
                System.out.println("Retrieved Data from Database:");
                System.out.println("Average Temperature: " + avgTemp);
                System.out.println("Average Humidity: " + avgHumidity);
                System.out.println("Average Rainfall: " + avgRainfall);

                // Add data to the dataset only if it is valid
                if (!rs.wasNull()) {
                    dataset.addValue(avgTemp, "Temperature", "Average Temperature");
                    dataset.addValue(avgHumidity, "Humidity", "Average Humidity");
                    dataset.addValue(avgRainfall, "Rainfall", "Average Rainfall");
                }
            } else {
                System.out.println("No data found in ClimateData table for the specified location or globally.");
            }

            // Check if the dataset has values
            if (dataset.getRowCount() > 0) {
                // Create the chart as a bar chart using the dataset
                JFreeChart chart = ChartFactory.createBarChart(
                        "Climate Trends",  // Chart title
                        "Category",        // X-axis label
                        "Average Value",   // Y-axis label
                        dataset            // Data
                );

                // Display the chart in a new window
                ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
                javax.swing.JFrame frame = new javax.swing.JFrame();
                frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(chartPanel);
                frame.pack();
                frame.setVisible(true);
            } else {
                System.out.println("No data available to display in the chart.");
            }

            // Close the connection
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error retrieving climate trends: " + e.getMessage());
        }
    }







    private static void viewResourceConsumptionReport(Scanner scanner) {
        try {
            // Prompt the user to choose whether to view data for a specific location or all data
            System.out.print("Do you want to view resource consumption for a specific location (Enter location ID) or all data? (Enter 'all' for all data): ");
            scanner.nextLine();  // This ensures we start with a clean input buffer

            String locationInput = scanner.nextLine();  // Read the input


            // Open a database connection
            Connection conn = DatabaseConnection.getConnection();

            String sql;
            // If user enters 'all', fetch the average usage for all resources
            if (locationInput.equalsIgnoreCase("all")) {
                sql = "SELECT resource_type, AVG(usage_amount) AS avg_usage FROM ResourceUsage GROUP BY resource_type";
            } else {
                // If a location ID is provided, fetch the average usage for that specific location
                int locationId = Integer.parseInt(locationInput);
                sql = "SELECT resource_type, AVG(usage_amount) AS avg_usage FROM ResourceUsage WHERE location_id = ? GROUP BY resource_type";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);

            // If location is specified, set the location ID parameter
            if (!locationInput.equalsIgnoreCase("all")) {
                stmt.setInt(1, Integer.parseInt(locationInput));
            }

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Create dataset for the chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("avg_usage"), rs.getString("resource_type"), "Average Usage");
            }

            // Create the chart using the dataset
            JFreeChart chart = ChartFactory.createBarChart(
                    "Resource Consumption Report",  // Title of the chart
                    "Resource Type",               // X-axis label
                    "Average Usage",               // Y-axis label
                    dataset                        // Dataset to be plotted
            );

            // Display the chart in a new window
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
            javax.swing.JFrame frame = new javax.swing.JFrame();
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(chartPanel);
            frame.pack();
            frame.setVisible(true);

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving resource consumption report: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid location ID input.");
        }
    }

    private static void setConfiguration(Scanner scanner) {
        try {
            scanner.nextLine(); // Consume any leftover newline character
            System.out.print("Enter location ID: ");
            int locationId = Integer.parseInt(scanner.nextLine()); // Get location ID from user

            System.out.print("Enter setting name: ");
            String settingName = scanner.nextLine(); // Get setting name

            System.out.print("Enter setting value: ");
            String settingValue = scanner.nextLine(); // Get setting value

            // Validate that the inputs are not empty
            if (settingName.isEmpty() || settingValue.isEmpty()) {
                System.out.println("Setting name and value cannot be empty.");
                return;
            }

            // Open a database connection
            Connection conn = DatabaseConnection.getConnection();

            // SQL query to insert or update the configuration setting with location_id
            String sql = "INSERT INTO SystemSettings (location_id, setting_name, setting_value) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE setting_value = VALUES(setting_value)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, locationId);          // Set location_id
            stmt.setString(2, settingName);      // Set setting_name
            stmt.setString(3, settingValue);     // Set setting_value

            // Execute the update (insert or update based on the primary key or unique index)
            stmt.executeUpdate();

            // Provide feedback to the user
            System.out.println("Configuration setting for location " + locationId + " saved successfully.");

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error saving configuration setting: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid location ID input. Please enter a valid integer.");
        }
    }

    // Method to check for alerts (user chooses location or all)
    private static void checkAlerts(Scanner scanner) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            // Ask if the user wants to check for a specific location or all locations
            System.out.print("Do you want to check alerts for a specific location? (yes/no): ");
            scanner.nextLine();  // This ensures we start with a clean input buffer

            String choice = scanner.nextLine();  // Read the input


            Integer locationId = null;
            if (choice.equals("yes")) {
                // Ask for location ID if user chooses "yes"
                System.out.print("Enter location ID: ");
                String locationInput = scanner.nextLine().trim();

                try {
                    locationId = Integer.parseInt(locationInput); // Convert input to integer
                } catch (NumberFormatException e) {
                    System.out.println("Invalid location ID. Checking for all locations.");
                }
            }

            // Call methods to check for climate and resource alerts
            checkClimateAlerts(conn, locationId); // Check climate-related alerts with location filter
            checkResourceAlerts(conn, locationId); // Check resource usage alerts with location filter

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error checking for alerts: " + e.getMessage());
        }
    }

    // Check for climate-related alerts (with location filter)
    private static void checkClimateAlerts(Connection conn, Integer locationId) throws SQLException {
        String locationFilter = (locationId != null) ? " WHERE location_id = " + locationId : "";

        // Check high temperature alerts
        String temperatureAlertSql = "SELECT * FROM ClimateData" + locationFilter + " WHERE temperature > 40";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(temperatureAlertSql);
        while (rs.next()) {
            System.out.println("ALERT! High Temperature: " + rs.getDouble("temperature") + "°C at Location ID: " + rs.getInt("location_id"));
        }

        // Check low humidity alerts
        String humidityAlertSql = "SELECT * FROM ClimateData" + locationFilter + " WHERE humidity < 20";
        rs = stmt.executeQuery(humidityAlertSql);
        while (rs.next()) {
            System.out.println("ALERT! Low Humidity: " + rs.getDouble("humidity") + "% at Location ID: " + rs.getInt("location_id"));
        }

        // Check high rainfall alerts
        String rainfallAlertSql = "SELECT * FROM ClimateData" + locationFilter + " WHERE rainfall > 100";
        rs = stmt.executeQuery(rainfallAlertSql);
        while (rs.next()) {
            System.out.println("ALERT! High Rainfall: " + rs.getDouble("rainfall") + "mm at Location ID: " + rs.getInt("location_id"));
        }

        // Check poor air quality alerts
        String airQualityAlertSql = "SELECT * FROM ClimateData" + locationFilter + " WHERE air_quality = 'Unhealthy' OR air_quality = 'Hazardous'";
        rs = stmt.executeQuery(airQualityAlertSql);
        while (rs.next()) {
            System.out.println("ALERT! Poor Air Quality: " + rs.getString("air_quality") + " at Location ID: " + rs.getInt("location_id"));
        }
    }

    // Check for resource usage alerts (with location filter)
    private static void checkResourceAlerts(Connection conn, Integer locationId) throws SQLException {
        String locationFilter = (locationId != null) ? " WHERE location_id = " + locationId : "";

        // Check high resource usage alerts
        String resourceAlertSql = "SELECT resource_type, usage_amount FROM ResourceUsage" + locationFilter + " WHERE usage_amount > 1000";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(resourceAlertSql);
        while (rs.next()) {
            System.out.println("ALERT! High " + rs.getString("resource_type") + " usage: " + rs.getDouble("usage_amount") + " at Location ID: " + rs.getInt("location_id"));
        }
    }

    // Method to view historical climate data for a specified month and year (user chooses location or all)
    private static void viewHistoricalClimateData(Scanner scanner) {
        try {
            // Get the month and year from the user
            System.out.print("Enter month (1-12): ");
            int month = scanner.nextInt();
            System.out.print("Enter year: ");
            int year = scanner.nextInt();

            // Ask if the user wants to view data for a specific location
            System.out.print("Do you want to view data for a specific location? (yes/no): ");
            scanner.nextLine();  // Consume newline
            String choice = scanner.nextLine().trim().toLowerCase();

            Integer locationId = null;
            if (choice.equals("yes")) {
                // Get the location ID if the user wants specific location data
                System.out.print("Enter location ID: ");
                locationId = scanner.nextInt();
            }

            // Connect to the database
            Connection conn = DatabaseConnection.getConnection();

            // SQL query with an optional location filter
            String locationFilter = (locationId != null) ? " AND location_id = ?" : "";
            String sql = "SELECT * FROM ClimateData WHERE MONTH(timestamp) = ? AND YEAR(timestamp) = ?" + locationFilter;

            // Prepare and execute the query
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            if (locationId != null) {
                stmt.setInt(3, locationId);  // Bind the location_id parameter
            }

            ResultSet rs = stmt.executeQuery();

            // Display the results
            System.out.println("Historical Climate Data:");
            boolean dataFound = false;
            while (rs.next()) {
                dataFound = true;
                System.out.printf("Timestamp: %s | Temperature: %.2f°C | Humidity: %.2f%% | Rainfall: %.2fmm | Air Quality: %s | Location ID: %d\n",
                        rs.getTimestamp("timestamp"), rs.getDouble("temperature"), rs.getDouble("humidity"),
                        rs.getDouble("rainfall"), rs.getString("air_quality"), rs.getInt("location_id"));
            }

            // If no data is found for the specified criteria
            if (!dataFound) {
                System.out.println("No historical data found for the specified month, year, and location.");
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving historical climate data: " + e.getMessage());
        }
    }

    // Method to view resource usage by a specified resource type (with an optional location filter)
    private static void viewResourceUsageByType(Scanner scanner) {
        try {
            // Prompt the user for the resource type
            System.out.print("Enter resource type (e.g., electricity, water, gas): ");
            String resourceType = scanner.next();

            // Ask if the user wants to view data for a specific location
            System.out.print("Do you want to view data for a specific location? (yes/no): ");
            scanner.nextLine();  // Consume newline
            String choice = scanner.nextLine().trim().toLowerCase();

            Integer locationId = null;
            if (choice.equals("yes")) {
                // Get the location ID if the user wants specific location data
                System.out.print("Enter location ID: ");
                locationId = scanner.nextInt();
            }

            // Connect to the database
            Connection conn = DatabaseConnection.getConnection();

            // SQL query with an optional location filter
            String locationFilter = (locationId != null) ? " AND location_id = ?" : "";
            String sql = "SELECT * FROM ResourceUsage WHERE resource_type = ?" + locationFilter;

            // Prepare the SQL statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, resourceType);
            if (locationId != null) {
                stmt.setInt(2, locationId);  // Bind the location_id parameter if it's provided
            }

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Display the results
            System.out.println("Resource Usage for Type: " + resourceType);
            boolean dataFound = false;
            while (rs.next()) {
                dataFound = true;
                System.out.printf("Timestamp: %s | Usage Amount: %.2f | Location ID: %d\n",
                        rs.getTimestamp("timestamp"), rs.getDouble("usage_amount"), rs.getInt("location_id"));
            }

            // If no data is found for the specified criteria
            if (!dataFound) {
                System.out.println("No resource usage data found for the specified resource type and location.");
            }

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving resource usage by type: " + e.getMessage());
        }
    }

    // Method to generate a custom monthly report
    private static void generateMonthlyReport(Scanner scanner) {
        // Define the base directory where the reports will be saved
        String reportDirectory = "C:\\Users\\Surya\\OneDrive\\Pictures\\climate_resource monthly reports";

        // Prompt the user for the month, year, and report type
        System.out.print("Enter the month (1-12) for the report: ");
        int month = scanner.nextInt();
        System.out.print("Enter the year for the report: ");
        int year = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Do you want a climate report, resource usage report, or both? (Enter 'climate', 'resource', or 'both'): ");
        String reportType = scanner.nextLine().toLowerCase();

        // Ask if the user wants the report for a specific location
        System.out.print("Do you want the report for a specific location? (yes/no): ");
        String locationChoice = scanner.nextLine().trim().toLowerCase();

        Integer locationId = null;
        String locationDirectory = "";
        if (locationChoice.equals("yes")) {
            System.out.print("Enter location ID: ");
            locationId = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            // Adjust the directory for specific locations
            locationDirectory = reportDirectory + "\\specific locations";
        }

        // Construct the file name with the selected month and year
        String monthYear = DateTimeFormatter.ofPattern("MMMM-yyyy").format(java.time.YearMonth.of(year, month));
        String fileName = reportDirectory + "\\Monthly_Report_" + monthYear + ".txt";  // Default for both

        // Adjust the file path based on the report type and location choice
        if (locationChoice.equals("yes")) {
            if (reportType.equals("climate")) {
                fileName = locationDirectory + "\\climate only loc\\Monthly_Report_" + monthYear + ".txt";
            } else if (reportType.equals("resource")) {
                fileName = locationDirectory + "\\resource only loc\\Monthly_Report_" + monthYear + ".txt";
            } else if (reportType.equals("both")) {
                fileName = locationDirectory + "\\both_with_location\\Monthly_Report_" + monthYear + ".txt";
            }
        } else {
            if (reportType.equals("climate")) {
                fileName = reportDirectory + "\\only climate\\Monthly_Report_" + monthYear + ".txt";
            } else if (reportType.equals("resource")) {
                fileName = reportDirectory + "\\only resource\\Monthly_Report_" + monthYear + ".txt";
            } else if (reportType.equals("both")) {
                fileName = reportDirectory + "\\both_no_loc\\Monthly_Report_" + monthYear + ".txt";
            }
        }

        // Ensure the report directory exists
        File directory = new File(fileName).getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            Connection conn = DatabaseConnection.getConnection();

            // Write the climate report if required
            if (reportType.equals("climate") || reportType.equals("both")) {
                String climateSql = "SELECT AVG(temperature) AS avg_temp, AVG(humidity) AS avg_humidity, AVG(rainfall) AS avg_rainfall " +
                        "FROM ClimateData WHERE MONTH(timestamp) = ? AND YEAR(timestamp) = ?";
                if (locationId != null) {
                    climateSql += " AND location_id = ?";
                }

                PreparedStatement climateStmt = conn.prepareStatement(climateSql);
                climateStmt.setInt(1, month);
                climateStmt.setInt(2, year);
                if (locationId != null) {
                    climateStmt.setInt(3, locationId);  // Bind the location_id if provided
                }

                ResultSet climateRs = climateStmt.executeQuery();

                writer.write("Monthly Climate Report:\n");
                if (climateRs.next()) {
                    writer.write(String.format("Month: %s | Avg Temperature: %.2f°C | Avg Humidity: %.2f%% | Avg Rainfall: %.2fmm\n",
                            monthYear, climateRs.getDouble("avg_temp"), climateRs.getDouble("avg_humidity"), climateRs.getDouble("avg_rainfall")));
                } else {
                    writer.write("No climate data available for the selected month and year.\n");
                }
            }

            // Write the resource usage report if required
            if (reportType.equals("resource") || reportType.equals("both")) {
                String resourceSql = "SELECT resource_type, AVG(usage_amount) AS avg_usage FROM ResourceUsage " +
                        "WHERE MONTH(timestamp) = ? AND YEAR(timestamp) = ?";
                if (locationId != null) {
                    resourceSql += " AND location_id = ?";  // Include location_id condition if provided
                }
                resourceSql += " GROUP BY resource_type";  // Ensure the resource_type is in the GROUP BY

                PreparedStatement resourceStmt = conn.prepareStatement(resourceSql);
                resourceStmt.setInt(1, month);
                resourceStmt.setInt(2, year);
                if (locationId != null) {
                    resourceStmt.setInt(3, locationId);  // Bind the location_id if provided
                }

                ResultSet resourceRs = resourceStmt.executeQuery();

                writer.write("\nMonthly Resource Usage Report:\n");
                boolean hasResourceData = false;
                while (resourceRs.next()) {
                    hasResourceData = true;
                    writer.write(String.format("Resource: %s | Avg Usage: %.2f\n",
                            resourceRs.getString("resource_type"), resourceRs.getDouble("avg_usage")));
                }
                if (!hasResourceData) {
                    writer.write("No resource usage data available for the selected month and year.\n");
                }
            }

            conn.close();
            System.out.println("Monthly report generated successfully at: " + fileName);

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error generating monthly report: " + e.getMessage());
        }
    }



    // Helper method to display charts
    private static void displayChart(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
// Database connection class
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/climateresourcetrackermain";
    private static final String USER = "root";
    private static final String PASSWORD = "harini@123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}



















