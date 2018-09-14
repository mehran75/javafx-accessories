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
