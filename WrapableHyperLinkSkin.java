
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.controlsfx.control.HyperlinkLabel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyHyperLinkLabelSkin extends BehaviorSkinBase<HyperlinkLabel, BehaviorBase<HyperlinkLabel>> {

    /***************************************************************************
     *
     * Static fields
     *
     **************************************************************************/

    // The strings used to delimit the hyperlinks
    private static final String HYPERLINK_START = "["; //$NON-NLS-1$
    private static final String HYPERLINK_END = "]"; //$NON-NLS-1$


    /***************************************************************************
     *
     * Private fields
     *
     **************************************************************************/

    private final TextFlow textFlow;
    private final EventHandler<ActionEvent> eventHandler = event -> {
        EventHandler<ActionEvent> onActionHandler = getSkinnable().getOnAction();
        if (onActionHandler != null) {
            onActionHandler.handle(event);
        }
    };
    private SimpleDoubleProperty maxWidthProperty = new SimpleDoubleProperty();
    private int padding = 0;


    /***************************************************************************
     *
     * Constructors
     *
     **************************************************************************/

    public MyHyperLinkLabelSkin(HyperlinkLabel control) {
        super(control, new BehaviorBase<>(control, Collections.emptyList()));

        this.textFlow = new TextFlow();

        getChildren().add(textFlow);

        updateText();

        registerChangeListener(control.textProperty(), "TEXT"); //$NON-NLS-1$
    }


    /***************************************************************************
     *
     * Implementation
     *
     **************************************************************************/

    @Override
    protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);

        if (p.equals("TEXT")) { //$NON-NLS-1$
            updateText();
        }
    }

    // splits up the string into Text and Hyperlink nodes, and places them
    // into a TextFlow instance
    private void updateText() {
        final String text = getSkinnable().getText();

        if (text == null || text.isEmpty()) {
            textFlow.getChildren().clear();
            return;
        }

        // parse the text and put it into an array list
        final List<Node> nodes = new ArrayList<>();

        int start = 0;
        final int textLength = text.length();
        while (start != -1 && start < textLength) {
            int startPos = text.indexOf(HYPERLINK_START, start);
            int endPos = text.indexOf(HYPERLINK_END, startPos);

            // if the startPos is -1, there are no more hyperlinks...
            if (startPos == -1 || endPos == -1) {
                // ...but there is still text to turn into one last label
                Label label = new Label(text.substring(start));
//                label.setEditable(false);
                label.maxWidthProperty().bind(maxWidthProperty);
                label.setAlignment(Pos.CENTER);
                nodes.add(label);
                break;
            }

            // firstly, create a label from start to startPos
            Text label = new Text(text.substring(start, startPos));
            label.setTextAlignment(TextAlignment.CENTER);
            nodes.add(label);

            // if endPos is greater than startPos, create a hyperlink
            Hyperlink hyperlink = new Hyperlink(text.substring(startPos + 1, endPos));
            hyperlink.setWrapText(true);
//            hyperlink.setPadding(new Insets(0, 0, 0, 0));
            hyperlink.setOnAction(eventHandler);
            hyperlink.maxWidthProperty().bind(maxWidthProperty);
            hyperlink.setAlignment(Pos.CENTER);
            hyperlink.setVisited(false);
            nodes.add(hyperlink);

            start = endPos + 1;
        }


        textFlow.getChildren().setAll(nodes);
        textFlow.maxWidthProperty().bind(maxWidthProperty.add(padding));
        textFlow.setTextAlignment(TextAlignment.CENTER);


    }


    public void setMaxWidth(double maxWidth) {
        this.maxWidthProperty.set(maxWidth);
    }

    public double getMaxWidthProperty() {
        return maxWidthProperty.get();
    }

    public SimpleDoubleProperty maxWidthPropertyProperty() {
        return maxWidthProperty;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
