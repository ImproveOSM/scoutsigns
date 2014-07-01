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
 * Created on Jun 17, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.skosigns.gui;

import java.awt.event.KeyEvent;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.TltCnf;
import org.openstreetmap.josm.tools.Shortcut;


/**
 * Displays the details related to a selected road sign in a
 * {@code ToggleDialog} window.
 * 
 * @author Bea
 * @version $Revision$
 */
public class SkoSignsDetailsDialog extends ToggleDialog {
    
    private static final long serialVersionUID = -4603746238296761716L;
    
    /** the toggle dialog window height */
    private static final int DLG_HEIGHT = 200;
    /** the shortcut which will be shown on the left side of JOSM */
    private static Shortcut sh = Shortcut.registerShortcut(GuiCnf.getInstance()
            .getDlgTitle(), TltCnf.getInstance().getPluginTlt(), KeyEvent.VK_F,
            Shortcut.ALT_SHIFT);
    
    
    /**
     * Builds a new {@code SkoSignsDetailsDialog} window with the default
     * settings.
     */
    public SkoSignsDetailsDialog() {
        super(GuiCnf.getInstance().getDlgTitle(), IconCnf.getInstance()
                .getShcName(), TltCnf.getInstance().getPluginTlt(), sh,
                DLG_HEIGHT);
    }
}