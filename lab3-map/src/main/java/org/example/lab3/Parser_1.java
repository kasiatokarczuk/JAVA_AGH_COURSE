package org.example.lab3;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;


public class Parser_1 {

 public static void main(String[] args) {

  try {
   File inputFile = new File("C:/Users/kasia/Desktop/points.xml/");
   SAXParserFactory factory = SAXParserFactory.newInstance();
   SAXParser saxParser = factory.newSAXParser();

   Handler_1 handler_1 = new Handler_1();

   saxParser.parse(inputFile, handler_1);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}