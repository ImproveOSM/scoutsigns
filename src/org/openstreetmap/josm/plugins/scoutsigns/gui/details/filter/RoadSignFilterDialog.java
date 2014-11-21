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
 * Created on Sep 22, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;


/**
 * Dialog window that displays the road sign filters.
 * 
 * @author Beata
 * @version $Revision$
 */
public class RoadSignFilterDialog extends JDialog {
    
    private static final long serialVersionUID = 7883099145424623783L;
    
    private static final Dimension DIM = new Dimension(370, 385);
    private RoadSignFilterPanel pnlFilter;
    
    
    /**
     * Builds a new {@code RoadSignFilterDialog}
     */
    public RoadSignFilterDialog() {
        setLayout(new BorderLayout());
        setModal(true);
        setTitle(GuiCnf.getInstance().getDlgFilterTitle());
        setIconImage(IconCnf.getInstance().getFilterIcon().getImage());
        setSize(DIM);
        setMinimumSize(DIM);
        
        /* add components */
        pnlFilter = new RoadSignFilterPanel();
        add(pnlFilter, BorderLayout.CENTER);
        add(new ButtonPanel(this), BorderLayout.SOUTH);
    }
    
    /**
     * Resets the search filters to the default ones.
     */
    void resetFilters() {
        pnlFilter.resetFilters();
    }
    
    /**
     * Returns the selected search filters.
     * 
     * @return {@code SearchFilter} object
     */
    SearchFilter getSelectedFilters() {
        return pnlFilter.getSelectedFilter();
    }
}