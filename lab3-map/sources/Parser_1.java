import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class Handler_1 extends DefaultHandler 
 {
  String loc_name;
  @Override
  public void startElement(String uri, 
                           String localName, 
			   String qName, 
			   Attributes attributes)
  throws SAXException 
   {
   
   
    System.out.println("Start Element :" + qName); 

    for (int i=0; i < attributes.getLength(); i++)
     {
      loc_name = attributes.getQName(i);   
      System.out.println("attr name: : " + loc_name + 
                         " value:" + attributes.getValue(loc_name));
			 
			                   

			 
			 
			 
     }     

   
    if (qName.equalsIgnoreCase("svg")) 
     {
     
		      
		      
     }
   
   }
   
  @Override
  public void endElement(String uri, 
                         String localName, 
		     String qName) 
  throws SAXException 
   {
    if (qName.equalsIgnoreCase("svg")) 
     {
      System.out.println("End Element :" + qName);
     }
   }   

				    
  @Override
  public void characters(char ch[], int start, int length) throws SAXException 
   {

    System.out.println(new String(ch, start, length));
   }

 }


public class Parser_1 
 {

  public static void main(String[] args) 
   {
   
    try 
     {
      File inputFile = new File("points.xml");
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
    
    
      Handler_1 handler_1 = new Handler_1();

      saxParser.parse(inputFile, handler_1);     
     } 
    catch (Exception e) 
     {
      e.printStackTrace();
     }
   }   

 }
