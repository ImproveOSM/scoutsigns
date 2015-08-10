package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.Properties;


/**
 * Utility class, holds UI tool tip texts.
 *
 * @author Bea
 * @version $Revision$
 */
public final class TltCnf {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_tlt.properties";

    private static final TltCnf INSTANCE = new TltCnf();

    public static TltCnf getInstance() {
        return INSTANCE;
    }

    private final String pluginTlt;

    private final String layerInfo;
    /* button panel tool tips */
    private final String btnFilter;
    private final String btnPhoto;
    private final String btnTrip;
    private final String btnComment;
    private final String btnMoreAction;
    private final String btnBack;
    private final String btnInvalid;
    private final String btnSolved;
    private final String btnDuplicate;


    private final String btnOpen;


    private TltCnf() {
        final Properties properties = CnfUtil.load(CNF_FILE);
        pluginTlt = CnfUtil.readProperty(properties, "plugin.tlt");
        layerInfo = CnfUtil.readProperty(properties, "layer.info");
        btnFilter = CnfUtil.readProperty(properties, "btn.filter");
        btnPhoto = CnfUtil.readProperty(properties, "btn.photo");
        btnTrip = CnfUtil.readProperty(properties, "btn.trip");
        btnComment = CnfUtil.readProperty(properties, "btn.comment");
        btnMoreAction = CnfUtil.readProperty(properties, "btn.more");
        btnBack = CnfUtil.readProperty(properties, "btn.back");
        btnInvalid = CnfUtil.readProperty(properties, "btn.invalid");
        btnSolved = CnfUtil.readProperty(properties, "btn.solved");
        btnDuplicate = CnfUtil.readProperty(properties, "btn.duplicate");
        btnOpen = CnfUtil.readProperty(properties, "btn.open");
    }

    public String getBtnBack() {
        return btnBack;
    }

    public String getBtnComment() {
        return btnComment;
    }

    public String getBtnDuplicate() {
        return btnDuplicate;
    }

    public String getBtnFilter() {
        return btnFilter;
    }

    public String getBtnInvalid() {
        return btnInvalid;
    }

    public String getBtnMoreAction() {
        return btnMoreAction;
    }

    public String getBtnOpen() {
        return btnOpen;
    }

    public String getBtnPhoto() {
        return btnPhoto;
    }

    public String getBtnSolved() {
        return btnSolved;
    }

    public String getBtnTrip() {
        return btnTrip;
    }

    public String getLayerInfo() {
        return layerInfo;
    }

    public String getPluginTlt() {
        return pluginTlt;
    }
}