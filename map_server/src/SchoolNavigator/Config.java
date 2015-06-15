package SchoolNavigator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private static File configFile = new File("config.xml");

    public static int threads = 10;
    public static int port = 1234;
    public static String mapfile = "map.xml";

    public static void load() throws JDOMException, IOException, NumberFormatException {
        if(!configFile.exists()) {
            Element root = new Element("config");
            Document config = new Document(root);
            config.getRootElement().addContent(new Element("threads").addContent(Integer.toString(threads)));
            config.getRootElement().addContent(new Element("port").addContent(Integer.toString(port)));
            config.getRootElement().addContent(new Element("mapfile").addContent(mapfile));
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.output(config, System.out);
            xmlOutputter.setFormat(Format.getPrettyFormat());
            xmlOutputter.output(config, new FileWriter(configFile.getName()));
        } else {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(configFile);
            Element config = document.getRootElement();
            threads = Integer.parseInt(config.getChild("threads").getText());
            port = Integer.parseInt(config.getChild("port").getText());
            mapfile = config.getChild("mapfile").getText();
        }
    }
}
