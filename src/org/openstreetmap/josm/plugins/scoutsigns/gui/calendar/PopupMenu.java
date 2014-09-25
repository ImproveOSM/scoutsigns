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
 * Created on Sep 24, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltCnf;


/**
 * Displays a date picker in a popup menu. The date picker is composed by: a
 * month selection header, and a day selection panel.
 * 
 * @author Beata
 * @version $Revision$
 */
class PopupMenu extends JPopupMenu implements DaySelectionObservable {
    
    private static final long serialVersionUID = -1962275558983116670L;
    
    private static final int COLS = 0;
    private static final int ROWS = 7;
    
    private static final Border SEL_BORDER = BorderFactory.createLineBorder(
            Color.red, 2);
    private static final Border DAY_MO_BORDER = BorderFactory.createLineBorder(
            Color.blue, 2);
    
    private Calendar calendar = Calendar.getInstance();
    
    private DaySelectionObserver observer;
    
    /* calendar components */
    private JPanel pnlHeader;
    private JLabel monthLabel;
    private JPanel pnlDays = null;
    
    private boolean monthChanged = false;
    
    /**
     * Builds a new popup menu.
     */
    PopupMenu() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        initPnlHeader();
        update();
        setLayout(new BorderLayout());
        add(BorderLayout.NORTH, pnlHeader);
    }
    
    private void initPnlHeader() {
        TltCnf tltCnf = TltCnf.getInstance();
        JLabel lblPrevMonth =
                Builder.buildLabel("<", tltCnf.getPrevMonth(),
                        SwingConstants.LEFT, Color.black, FontUtil.BOLD_12);
        lblPrevMonth.addMouseListener(new MonthSelectionListener(lblPrevMonth,
                -1));
        monthLabel =
                Builder.buildLabel("", "", SwingConstants.CENTER, Color.black,
                        FontUtil.BOLD_12);
        JLabel lblNextMonth =
                Builder.buildLabel(">", tltCnf.getNextMonth(),
                        SwingConstants.RIGHT, Color.black, FontUtil.BOLD_12);
        lblNextMonth.addMouseListener(new MonthSelectionListener(lblNextMonth,
                1));
        pnlHeader =
                Builder.buildBoxLayoutPanel(lblPrevMonth, monthLabel,
                        lblNextMonth);
    }
    
    /**
     * Updates the currently displayed calendar.
     */
    void update() {
        monthLabel.setText(DateUtil.formatMonth(calendar.getTimeInMillis()));
        if (pnlDays != null) {
            remove(pnlDays);
        }
        initPnlDays();
        setBackground(Color.white);
        add(BorderLayout.CENTER, pnlDays);
        pack();
    }
    
    /**
     * Sets the calendar to the given time.
     * 
     * @param time a {@code String} representing a time
     */
    void setTime(String time) {
        Date date = DateUtil.parseDay(time);
        if (date != null) {
            calendar.setTime(date);
            monthChanged = false;
        }
    }
    
    /**
     * Updates the calendar currently selected day.
     * 
     * @param day the day of month
     */
    void setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        observer.setDay(calendar.getTime());
    }
    
    @Override
    public void registerObserver(DaySelectionObserver observer) {
        this.observer = observer;
    }
    
    @Override
    public void notifyObserver(Date day) {
        notifyObserver(day);
    }
    
    
    private void initPnlDays() {
        pnlDays = new JPanel(new GridLayout(COLS, ROWS));
        pnlDays.setBackground(Color.white);
        pnlDays.setOpaque(true);
        
        // init days header
        Calendar setupCalendar = (Calendar) calendar.clone();
        setupCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        setupCalendar.set(Calendar.DAY_OF_WEEK,
                setupCalendar.getFirstDayOfWeek());
        for (int i = 0; i < ROWS; i++) {
            DayName dayName =
                    DayName.getDayName(setupCalendar.get(Calendar.DAY_OF_WEEK));
            Color txtColor = dayName == DayName.SUN ? Color.blue : Color.black;
            JLabel label =
                    Builder.buildLabel(dayName.toString(), null,
                            SwingConstants.CENTER, txtColor, FontUtil.BOLD_12);
            pnlDays.add(label);
            setupCalendar.roll(Calendar.DAY_OF_WEEK, true);
        }
        
        // init days content
        setupCalendar = (Calendar) calendar.clone();
        setupCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int first = setupCalendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < (first - 1); i++) {
            pnlDays.add(new JLabel(""));
        }
        int selDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= setupCalendar
                .getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            JLabel label =
                    Builder.buildLabel(String.valueOf(i), null,
                            SwingConstants.CENTER, Color.black,
                            FontUtil.PLAIN_12);
            if (!monthChanged) {
                if (i == dayOfMonth) {
                    label.setOpaque(true);
                    label.setBorder(DAY_MO_BORDER);
                } else if (selDayOfMonth == i) {
                    label.setOpaque(true);
                    label.setBorder(SEL_BORDER);
                }
            }
            label.addMouseListener(new DaySelectionListener(this, label));
            pnlDays.add(label);
        }
    }
    
    
    private final class MonthSelectionListener extends MouseAdapter {
        
        private JLabel lbl;
        private int amount;
        
        private MonthSelectionListener(JLabel lbl, int amount) {
            this.lbl = lbl;
            this.amount = amount;
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            calendar.add(Calendar.MONTH, amount);
            monthChanged = true;
            update();
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            lbl.setBorder(new EtchedBorder());
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            lbl.setBorder(new EmptyBorder(new EtchedBorder()
                    .getBorderInsets(new JLabel())));
        }
    }
}