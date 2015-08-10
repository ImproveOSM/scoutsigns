package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 *
 * @author Beata
 * @version $Revision$
 */
class TypeFilterPanel extends JPanel {

    private static final long serialVersionUID = 4445057324599948957L;

    private static final int ROW = 0;
    private static final int COL = 4;
    private static final Dimension DIM = new Dimension(100, 200);

    private ButtonGroup group;
    private final List<TypeRadioButton> buttons = new ArrayList<>();


    /**
     * Builds a new {@code TypeFilterPanel} object.
     *
     * @param selection the selected type
     */
    TypeFilterPanel(final Map<String, ImageIcon> typesMap, final String selection) {
        setLayout(new GridLayout(ROW, COL));
        setSize(DIM);
        addComponents(typesMap);
        selectElement(selection);
        setBackground(Color.white);
    }

    private void addComponents(final Map<String, ImageIcon> typesMap) {
                group = new ButtonGroup();
                for (final Map.Entry<String, ImageIcon> entry : typesMap.entrySet()) {
                    final TypeRadioButton btn = new TypeRadioButton(entry.getKey(), entry.getValue());
                    buttons.add(btn);
                    group.add(btn.getRbSelection());
                    add(btn);
                }
            }


    private void selectElement(final String selection) {
                if (selection != null) {
                    for (final TypeRadioButton btn : buttons) {
                        if (btn.getType().equals(selection)) {
                            btn.setSelected(true);
                            break;
                        }
                    }
                }
            }

    /**
             * Clears previous selections.
             */
            void clearSelection() {
                group.clearSelection();
                repaint();
            }

    /**
             * Returns the selected type.
             *
             * @return a string
             */
            String getSelection() {
                String selection = null;
                for (final TypeRadioButton btn : buttons) {
                    if (btn.getRbSelection().isSelected()) {
                        selection = btn.getType();
                        break;
                    }
                }
                return selection;
            }

    void update(final Map<String, ImageIcon> typesMap) {
                removeAll();
                addComponents(typesMap);
            }
}