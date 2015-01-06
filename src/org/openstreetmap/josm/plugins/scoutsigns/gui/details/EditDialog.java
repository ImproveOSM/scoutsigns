/*
 * Copyright (c) 2014, skobbler GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Sep 25, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.CancelAction;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.ModalDialog;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DuplicateIdVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObservable;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


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
    
    private Status status;
    
    
    /**
     * Builds a new {@code CommentDialog} object
     */
    EditDialog(Status status, String title, Image image) {
        super(title, image, DIM);
        this.status = status;
        createComponents();
    }
    
    
    @Override
    protected void createComponents() {
        if (status == Status.DUPLICATE) {
            addDuplicateId();
        }
        addComment();
        addBtnPnl();
    }
    
    private void addDuplicateId() {
        txtDuplicateId = Builder.buildTextField(null, null, Color.white);
        txtDuplicateId.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        JLabel lblDuplError = Builder.buildLabel(GuiCnf.getInstance().
                getTxtDuplIdInvalid(),FontUtil.BOLD_12, Color.red, false);
        txtDuplicateId.setInputVerifier(new DuplicateIdVerifier(txtDuplicateId,
                lblDuplError));
        
        JPanel pnlDuplicate = new JPanel(new GridLayout(1, 0, 1, 1));
        pnlDuplicate.add(txtDuplicateId);
        pnlDuplicate.add(lblDuplError);
        
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBorder(BORDER);
        pnlNorth.add(Builder.buildLabel(GuiCnf.getInstance().getLblDupl(),
                FontUtil.BOLD_12, null), BorderLayout.LINE_START);
        pnlNorth.add(pnlDuplicate, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }
    
    private void addComment() {
        txtComment = Builder.buildTextArea(null, true, FontUtil.PLAIN_12,
                Color.white, null);
        
        JPanel pnlComment = new JPanel(new BorderLayout());
        pnlComment.setBorder(BORDER);
        pnlComment.add(Builder.buildScrollPane(txtComment, Color.white, true),
                BorderLayout.CENTER);
        pnlComment.setVerifyInputWhenFocusTarget(true);
        add(pnlComment, BorderLayout.CENTER);
    }
    
    private void addBtnPnl() {
        lblCommentError = Builder.buildLabel(GuiCnf.getInstance().
                getTxtCommentInvalid(),FontUtil.BOLD_12, Color.red, false);
        
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        pnlBtn.add(Builder.buildButton(new AddCommentAction(), GuiCnf
                .getInstance().getBtnOk()));
        pnlBtn.add(Builder.buildButton(new CancelAction(this), GuiCnf
                .getInstance().getBtnCancel()));
        
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.add(lblCommentError, BorderLayout.LINE_START);
        pnlSouth.add(pnlBtn, BorderLayout.LINE_END);
        add(pnlSouth, BorderLayout.SOUTH);
    }
    
    @Override
    public void registerObserver(StatusChangeObserver observer) {
        this.observer = observer;
    }
    
    @Override
    public void notifyObserver(String username, String text, Status status,
            Long duplicateOf) {
        if (observer != null) {
            observer.statusChanged(username, text, status, duplicateOf);
        }
    }
    
    
    /*
     * Creates a new comment for the selected road sign. A comment is created
     * only if the comment component contains a text.
     */
    private final class AddCommentAction extends AbstractAction {
        
        private static final long serialVersionUID = -5351629052918137710L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            // if input is valid load username & create comment
            if (validInput()) {
                Long duplicateId = (status == Status.DUPLICATE) ? 
                        Long.parseLong(txtDuplicateId.getText().trim()) : null;
                
                if (lblCommentError.isVisible()) {
                    lblCommentError.setVisible(false);
                }
                dispose();
                
                // load username
                String username = PrefManager.getInstance().loadOsmUsername();
                if (username.isEmpty()) {
                    String nemUsername = JOptionPane.showInputDialog(Main.parent, 
                            GuiCnf.getInstance().getTxtUsernameWarning(),
                            GuiCnf.getInstance().getDlgWarningTitle(),
                            JOptionPane.WARNING_MESSAGE);
                    if (nemUsername != null && !nemUsername.isEmpty()) {
                        PrefManager.getInstance().saveOsmUsername(nemUsername);
                        notifyObserver(nemUsername, txtComment.getText().trim(), 
                                status, duplicateId);
                    }
                } else {
                    notifyObserver(username, txtComment.getText().trim(),
                            status, duplicateId);
                }
            }
        }
        
        private boolean validInput() {
            boolean valid = true;
            if (status == Status.DUPLICATE && 
                    !txtDuplicateId.getInputVerifier().verify(txtDuplicateId)) {
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