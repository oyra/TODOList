package com.oyra;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.oyra.todolist.data");
        schema.enableKeepSectionsByDefault();
        addItem(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java"); //could be ../app/src/main/java
    }

    private static void addItem(Schema schema) {
        Entity note = schema.addEntity("Item");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addBooleanProperty("isComplete").notNull();
        note.addLongProperty("timestamp").index().notNull();
    }


}
