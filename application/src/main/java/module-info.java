module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires javafx.media;
    requires net.bytebuddy;
    requires jdk.compiler;
    requires java.desktop;

    requires api;

    opens app to javafx.fxml;

    exports app;
    exports app.deck;
    exports app.state;
    exports app.card;
    exports app.store;
}