package ru.academit.podlatov.csv;

import java.io.*;

public class CSVToHTML {
    private static void writeBottom(PrintWriter writer) {
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }

    private static void writeTop(PrintWriter writer) {
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset=\"utf-8\">");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<meta charset=\"utf-8\">");
        writer.println("<table>");
    }

    public static void main(String[] args) throws IOException {
        String inputPath = "CSV/src/ru/academit/podlatov/csv/file.csv";
        String outputPath = "CSV/src/ru/academit/podlatov/csv/out.html";

        final char comma = ',';
        final char quote = '"';
        final String rowStart = "<tr>";
        final String rowEnd = "</tr>";
        final String cellStart = "<td>";
        final String cellEnd = "</td>";
        final String toNextLine = "<br/>";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             PrintWriter writer = new PrintWriter(outputPath)) {
            writeTop(writer);

            String line;
            boolean isSpecialArea = false;

            while ((line = reader.readLine()) != null) {
                int lastIndex = line.length() - 1;

                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);

                    if (!isSpecialArea) {
                        if (i == 0) {
                            writer.print(rowStart + cellStart);

                            if (currentChar == quote) {
                                isSpecialArea = true;
                                continue;
                            }
                        }

                        if (currentChar == comma && i == lastIndex) {
                            writer.print(cellEnd + cellStart);
                            continue;
                        }

                        if (currentChar == comma && i < lastIndex && line.charAt(i + 1) == quote) {
                            writer.print(cellEnd + cellStart);
                            i++;
                            isSpecialArea = true;
                            continue;
                        }

                        if (currentChar == comma && i < lastIndex && line.charAt(i + 1) != quote) {
                            writer.print(cellEnd + cellStart);
                            //   i++;
                            continue;
                        }
                    } else {
                        if (currentChar == quote) {
                            if (i < lastIndex && line.charAt(i + 1) == quote) {
                                writer.print(currentChar);
                                i++;
                                continue;
                            } else if (i < lastIndex - 1 && line.charAt(i + 1) == comma && line.charAt(i + 2) == quote) {
                                writer.print(cellEnd + cellStart);
                                i += 2;
                                continue;
                            } else if (i < lastIndex && line.charAt(i + 1) == comma) {
                                writer.print(cellEnd + cellStart);
                                i++;
                                isSpecialArea = false;
                                continue;
                            } else if (i == lastIndex) {
                                i++;
                                isSpecialArea = false;
                                continue;
                            }
                        }
                    }

                    if (currentChar == '<') {
                        writer.print("&lt;");
                        continue;
                    } else if (currentChar == '>') {
                        writer.print("&gt;");
                        continue;
                    } else if (currentChar == '&') {
                        writer.print("&amp;");
                        continue;
                    }

                    writer.print(currentChar);
                }

                if (isSpecialArea) {
                    writer.println(toNextLine);
                } else {
                    writer.println(cellEnd + rowEnd);
                }

            }
            writeBottom(writer);
        }
    }
}