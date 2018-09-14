# javafx-accessories
this repository made for saving my needed classed or components which I may use in the future  


# WrapableHyperLinkSkin.java

    this is a modifyed skin for ControllsFX HyperLinkLabel component which allow I fixed its wrapping problem.
    example:
    
        String string = "some dumy text";
        HyperLinkLabel text = new HyperLinkLabel();
        MyHyperLinkLabelSkin linkLabelSkin = new MyHyperLinkLabelSkin(string);
        linkLabelSkin.setMaxWidth(330);
        linkLabelSkin.setPadding(10);
        text.setSkin(linkLabelSkin);
        
        
# UrlDetector.java

    This class can detect URLs in a string like example below:
        
         String rawText = "some texts and the link is https://github.com/mehran75/javafx-accessories";
         String result = UrlDetector.detectURL(rawText);
         result will be like this:
                "some texts and the link is [https://github.com/mehran75/javafx-accessories]"
         which can be use for HyperLinkLabel component



# VoiceRecorder.java

    Record Mp3 Sound
    
    libraries:
        1. mp3agic
        maven :
         <dependency>
            <groupId>com.mpatric</groupId>
            <artifactId>mp3agic</artifactId>
            <version>0.9.1</version>
        </dependency>
        
        2. jave-1.0.2.jar
            jar file exists in this repo
