/*
 *  Copyright 2015 Telenav, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DuplicateIdVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObservable;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import com.telenav.josm.common.gui.CancelAction;
import com.telenav.josm.common.gui.ModalDialog;
import com.telenav.josm.common.gui.builder.ButtonBuilder;
import com.telenav.josm.common.gui.builder.ContainerBuilder;
import com.telenav.josm.common.gui.builder.LabelBuilder;
import com.telenav.josm.common.gui.builder.TextComponentBuilder;


/**
 * Dialog window that displays a comment panel.
 *
 * @author Beata
 * @version $Revision$
 */
class EditDialog extends ModalDialog implements StatusChangeObservable {

    private static final long serialVersionUID = 4145954527837092488L;

    /* minimum dimension */
    private static final Dimension DIM = new Dimension(300, 200);

    /* border to be added around the comment text component */
    private static final Border BORDER = new EmptyBorder(5, 5, 2, 5);
    private StatusChangeObserver observer;
    private JLabel lblCommentError;
    private JTextField txtDuplicateId;
    private JTextArea txtComment;

    private final Status status;


    /**
     * Builds a new {@code CommentDialog} object
     */
    EditDialog(final Status status, final String title, final Image image) {
        super(title, image, DIM);
        setLocationRelativeTo(MainApplication.getMap().mapView);
        this.status = status;
        createComponents();
    }

    @Override
    public void notifyObserver(final String username, final String text, final Status status, final Long duplicateOf) {
        if (observer != null) {
            observer.statusChanged(username, text, status, duplicateOf);
        }
    }

    @Override
    public void registerObserver(final StatusChangeObserver observer) {
        this.observer = observer;
    }

    @Override
    protected void createComponents() {
        if (status == Status.DUPLICATE) {
            addDuplicateId();
        }
        addComment();
        addBtnPnl();
    }

    private void addBtnPnl() {
        lblCommentError = LabelBuilder.build(GuiConfig.getInstance().getTxtCommentInvalid(), Font.BOLD, Color.red,
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP, false);
        final JPanel pnlBtn = ContainerBuilder.buildFlowLayoutPanel(FlowLayout.TRAILING,
                ButtonBuilder.build(new AddCommentAction(), GuiConfig.getInstance().getBtnOk()),
                ButtonBuilder.build(new CancelAction(this), GuiConfig.getInstance().getBtnCancel()));
        final JPanel pnlSouth = ContainerBuilder.buildBorderLayoutPanel(lblCommentError, pnlBtn, null);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addComment() {
        txtComment = TextComponentBuilder.buildTextArea(Color.WHITE, Font.PLAIN, true);
        final JScrollPane scrollPane = ContainerBuilder.buildScrollPane(txtComment, Color.white,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray));

        final JPanel pnlComment = ContainerBuilder.buildBorderLayoutPanel(null, scrollPane, null, BORDER);
        pnlComment.setVerifyInputWhenFocusTarget(true);
        add(pnlComment, BorderLayout.CENTER);
    }


    private void addDuplicateId() {
        txtDuplicateId = TextComponentBuilder.buildTextField(null, Font.PLAIN, Color.white,
                BorderFactory.createLineBorder(Color.gray));

        final JLabel lblDuplError = LabelBuilder.build(GuiConfig.getInstance().getTxtDuplIdInvalid(), Font.PLAIN,
                Color.red, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP, false);
        txtDuplicateId.setInputVerifier(new DuplicateIdVerifier(txtDuplicateId, lblDuplError));

        final JPanel pnlDuplicate = new JPanel(new GridLayout(1, 0, 1, 1));
        pnlDuplicate.add(txtDuplicateId);
        pnlDuplicate.add(lblDuplError);

        final JLabel lblDupl = LabelBuilder.build(GuiConfig.getInstance().getLblDupl(), Font.BOLD, getBackground(),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP, true);
        final JPanel pnlNorth = ContainerBuilder.buildBorderLayoutPanel(lblDupl, null, BORDER);
        pnlNorth.add(pnlDuplicate, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }


    /*
     * Creates a new comment for the selected road sign. A comment is created only if the comment component contains a
     * text.
     */
    private class AddCommentAction extends AbstractAction {

        private static final long serialVersionUID = -5351629052918137710L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            // if input is valid load username & create comment
            if (validInput()) {
                final Long duplicateId =
                        (status == Status.DUPLICATE) ? Long.parseLong(txtDuplicateId.getText().trim()) : null;

                        if (lblCommentError.isVisible()) {
                            lblCommentError.setVisible(false);
                        }
                        dispose();

                        // load username
                        final String username = PrefManager.getInstance().loadOsmUsername();
                        if (username.isEmpty()) {
                            final String nemUsername =
                                    JOptionPane.showInputDialog(Main.parent, GuiConfig.getInstance().getTxtUsernameWarning(),
                                            GuiConfig.getInstance().getDlgWarningTitle(), JOptionPane.WARNING_MESSAGE);
                            if (nemUsername != null && !nemUsername.isEmpty()) {
                                PrefManager.getInstance().saveOsmUsername(nemUsername);
                                notifyObserver(nemUsername, txtComment.getText().trim(), status, duplicateId);
                            }
                        } else {
                            notifyObserver(username, txtComment.getText().trim(), status, duplicateId);
                        }
            }
        }

        private boolean validInput() {
            boolean valid = true;
            if (status == Status.DUPLICATE && !txtDuplicateId.getInputVerifier().verify(txtDuplicateId)) {
                valid = false;
            }

            if (txtComment.getText().trim().isEmpty()) {
                lblCommentError.setVisible(true);
                valid = false;
            }
            return valid;
        }
    }
}