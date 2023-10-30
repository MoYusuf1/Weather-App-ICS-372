package edu.metrostate.utils;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.application.Application.launch;

// Found this class on StackOverflow useful to look at JavaFX CSS metadata
public class CssMetaDataExtractor extends Application {
    private Stage primaryStageLink;
    private Scene primaryScene;
    private SplitPane rootNode;
    private Group group;
    private TreeView<String> structureTV;
    private Component structureRoot;
    private TreeView<String> nodePropertiesTV;
    private TreeItem<String> nodePropertiesRoot;
    private TreeTableView<CMDComponent> cssPropertiesTV;
    private TreeItem<CMDComponent> cssPropertiesRoot;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStageLink = primaryStage;
        createContent();
        primaryScene.getStylesheets().add( CssMetaDataExtractor.class.getResource("test.css").toExternalForm() );
        /* Create and tune your element here... */
        ProgressBar pb = new ProgressBar();
        /* ...and add element here... */
        processElement( pb );
    }

    private void processElement(Node node) {
        group.getChildren().add(node);
        primaryStageLink.show();
        if ( node instanceof Parent) {
            extractCssMetaData((Parent)node, null);
            node.addEventHandler(Event.ANY, (Event event) -> {
                Component selected = (Component)structureTV.getSelectionModel().selectedItemProperty().getValue();
                showCssProperties( selected );
                showNodeProperties( selected );
            });
        }
    }
    private void extractCssMetaData(Parent node, Component parent) {
        Component currentParent = parent;
        if (parent == null) {
            structureRoot = new Component( node );
            currentParent = structureRoot;
            primaryStageLink.setTitle("CssMetaDataExtractor for [" + node.getClass().getName() + "]");
        }
        for (Node subNode : node.getChildrenUnmodifiable()) {
            Component child = new Component( subNode );
            currentParent.getChildren().add( child );
            if (subNode instanceof Parent) {
                extractCssMetaData((Parent)subNode, child);
            }
        }
        structureRoot.setExpanded(true);
        structureTV.setRoot(structureRoot);
        if ( structureTV.getSelectionModel().getSelectedItems().size() == 0 ) {
            structureTV.getSelectionModel().selectFirst();
        }
    }
    private void showNodeProperties(Component component) {
        nodePropertiesRoot = new TreeItem( component.getNode().getClass().getName() );
        TreeItem<String> selector = new TreeItem("Selector");
        selector.setExpanded(true);
        selector.getChildren().add( new TreeItem( getSelectorHierarchy(component) ) );
        nodePropertiesRoot.getChildren().add( selector );
        if ( component.getNode().getPseudoClassStates() != null && component.getNode().getPseudoClassStates().size() > 0 ) {
            TreeItem<String> pseudoClasses = new TreeItem("PseudoClass States");
            pseudoClasses.setExpanded(true);
            for ( PseudoClass pc : component.getNode().getPseudoClassStates() ) {
                pseudoClasses.getChildren().add( new TreeItem( pc.getPseudoClassName() + " [" + pc.getClass().getName() + "]" ) );
            }
            nodePropertiesRoot.getChildren().add( pseudoClasses );
        }
        if ( component.getNode().getStyle() != null && component.getNode().getStyle().length() > 0 ) {
            TreeItem<String> style = new TreeItem("Style");
            style.setExpanded(true);
            style.getChildren().add( new TreeItem( component.getNode().getStyle() ) );
            nodePropertiesRoot.getChildren().add( style );
        }
        if ( component.getNode().getTypeSelector() != null & component.getNode().getTypeSelector().length() > 0 ) {
            TreeItem<String> typeSelector = new TreeItem("Type Selector");
            typeSelector.setExpanded(true);
            typeSelector.getChildren().add( new TreeItem( component.getNode().getTypeSelector() ) );
            nodePropertiesRoot.getChildren().add( typeSelector );
        }
        if ( component.getNode().getId() != null && component.getNode().getId().length() > 0 ) {
            TreeItem<String> id = new TreeItem("Id");
            id.setExpanded(true);
            id.getChildren().add( new TreeItem( component.getNode().getId() ) );
            nodePropertiesRoot.getChildren().add( id );
        }
        if ( component.getNode().getProperties() != null && component.getNode().getProperties().size() > 0 ) {
            TreeItem<String> properties = new TreeItem("Properties");
            properties.setExpanded(true);
            for ( Object key : component.getNode().getProperties().keySet() ) {
                properties.getChildren().add( new TreeItem( key + "=" + component.getNode().getProperties().get(key) ) );
            }
            nodePropertiesRoot.getChildren().add( properties );
        }
        if ( component.getNode().getAccessibleRole() != null ) {
            TreeItem<String> accessibleRole = new TreeItem("Accessible Role");
            accessibleRole.setExpanded(true);
            accessibleRole.getChildren().add( new TreeItem( component.getNode().getAccessibleRole() ) );
            nodePropertiesRoot.getChildren().add( accessibleRole );
            if ( component.getNode().getAccessibleRoleDescription() != null && component.getNode().getAccessibleRoleDescription().length() > 0 ) {
                accessibleRole.getChildren().add( new TreeItem( component.getNode().getAccessibleRoleDescription() ) );
            }
        }
        if ( component.getNode().getAccessibleText() != null && component.getNode().getAccessibleText().length() > 0 ) {
            TreeItem<String> accessibleText = new TreeItem("Accessible Text");
            accessibleText.setExpanded(true);
            accessibleText.getChildren().add( new TreeItem( component.getNode().getAccessibleText() ) );
            nodePropertiesRoot.getChildren().add( accessibleText );
        }
        nodePropertiesRoot.setExpanded(true);
        nodePropertiesTV.setRoot(nodePropertiesRoot);
        nodePropertiesTV.setShowRoot(false);
    }
    private String getSelectorHierarchy(Component component) {
        Component current = component;
        StringBuilder selectors = new StringBuilder();
        while (true) {
            StringBuilder sb = new StringBuilder();
            if ( current.getNode().getStyleClass().size() > 1 ) {
                sb.append("[ ");
                for ( String styleClass : current.getNode().getStyleClass() ) {
                    sb.append(".").append(styleClass).append(" | ");
                }
                sb.delete(sb.length()-3, sb.length());
                sb.append(" ]");
            } else if ( current.getNode().getStyleClass().size() == 1 ) {
                sb.append(".").append( current.getNode().getStyleClass() );
            } else {
                sb.append("NULL");
            }
            if ( current.getParent() != null ) {
                sb.insert(0, " > ");
                selectors.insert(0, sb);
                current = (Component)current.getParent();
            } else {
                selectors.insert(0, sb);
                break;
            }
        }
        return selectors.toString();
    }
    private void showCssProperties(Component component) {
        if ( component.getNode().getCssMetaData() != null && component.getNode().getCssMetaData().size() > 0 ) {
            cssPropertiesRoot = new TreeItem<CMDComponent>( new CMDComponent() );
            for ( CssMetaData cmd : component.getNode().getCssMetaData() ) {
                addProperty(cmd, component.getNode(), cssPropertiesRoot);
            }
            cssPropertiesRoot.setExpanded(true);
            cssPropertiesTV.setRoot(cssPropertiesRoot);
            cssPropertiesTV.setShowRoot(false);
        }
    }
    private void addProperty(CssMetaData cmd, Node node, TreeItem<CMDComponent> parent) {
        TreeItem<CMDComponent> child = new TreeItem<CMDComponent>( new CMDComponent(cmd, node) );
        parent.getChildren().add( child );
        if ( cmd.getSubProperties() != null ) {
            for ( Object subcmd : cmd.getSubProperties() ) {
                if ( subcmd instanceof CssMetaData ) {
                    addProperty( (CssMetaData)subcmd, node, child );
                }
            }
        }
    }
    private class Component extends TreeItem<String> {
        private Node node;
        public Component(Node node) {
            super(node.getClass().getName());
            this.node = node;
        }
        public Node getNode() { return node; }
        public void setNode(Node node) { this.node = node; }
    }
    private class CMDComponent {
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty converterName = new SimpleStringProperty();
        private SimpleStringProperty initialValue = new SimpleStringProperty();
        private SimpleStringProperty styledValue = new SimpleStringProperty();
        private Node node;
        public CMDComponent() {
            this.name.set("none");
            this.converterName.set("none");
            this.initialValue.set("none");
            this.styledValue.set("none");
        }
        public CMDComponent(CssMetaData cssMetaData, Node node) {
            this.name.set(cssMetaData.getProperty());
            this.converterName.set(cssMetaData.getConverter().toString());
            if ( cssMetaData.getInitialValue(node) != null ) {
                this.initialValue.set(cssMetaData.getInitialValue(node).toString());
            } else {
                this.initialValue.set("null");
            }
            if ( cssMetaData.getStyleableProperty(node) != null ) {
                if ( cssMetaData.getStyleableProperty(node).getValue() != null ) {
                    this.styledValue.set( cssMetaData.getStyleableProperty(node).getValue().toString() );
                } else {
                    this.styledValue.set("null");
                }
            } else {
                this.styledValue.set("null");
            }
            this.node = node;
        }
        public String getName() { return name.get(); }
        public void setName(String name) { this.name.set(name); }
        public String getConverterName() { return converterName.get(); }
        public void setConverterName(String converterName) { this.converterName.set(converterName); }
        public String getInitialValue() { return initialValue.get(); }
        public void setInitialValue(String initialValue) { this.initialValue.set(initialValue); }
        public String getStyledValue() { return styledValue.get(); }
        public void setStyledValue(String styledValue) { this.styledValue.set(styledValue); }
    }
    private void createContent() {
        rootNode = new SplitPane();
        rootNode.setOrientation(Orientation.HORIZONTAL);
        rootNode.setDividerPosition(0, 0.25);
        primaryScene = new Scene(rootNode, 1300, 900);
        primaryStageLink.setScene(primaryScene);
        primaryStageLink.initStyle(StageStyle.DECORATED);
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPosition(0, 0.15);
        splitPane.setDividerPosition(1, 0.6);
        rootNode.getItems().add(splitPane);
        ScrollPane scrollPane = new ScrollPane();
        group = new Group();
        scrollPane.setContent(group);
        splitPane.getItems().add(scrollPane);
        structureTV = new TreeView<>();
        structureTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        structureTV.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) -> {
                    showCssProperties( (Component)newValue );
                    showNodeProperties( (Component)newValue );
                }
        );
        splitPane.getItems().add(structureTV);
        nodePropertiesTV = new TreeView<>();
        splitPane.getItems().add(nodePropertiesTV);
        cssPropertiesTV = new TreeTableView();
        TreeTableColumn<CMDComponent, String> nameColumn = new TreeTableColumn<>("Name");
        nameColumn.setPrefWidth(250);
        nameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CMDComponent, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );
        TreeTableColumn<CMDComponent, String> initialColumn = new TreeTableColumn<>("Initial Value");
        initialColumn.setPrefWidth(275);
        initialColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CMDComponent, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getInitialValue() )
        );
        TreeTableColumn<CMDComponent, String> styledColumn = new TreeTableColumn<>("Styled Value");
        styledColumn.setPrefWidth(275);
        styledColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CMDComponent, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getStyledValue() )
        );
        TreeTableColumn<CMDComponent, String> converterColumn = new TreeTableColumn<>("Default Converter");
        converterColumn.setPrefWidth(170);
        converterColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CMDComponent, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getConverterName() )
        );
        cssPropertiesTV.getColumns().setAll(nameColumn, initialColumn, styledColumn, converterColumn);
        rootNode.getItems().add(cssPropertiesTV);
    }}