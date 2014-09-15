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
 * Created on Jul 31, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.pref;

import org.openstreetmap.josm.Main;


/**
 * Utility class, manages save and load (put & get) operations of the preference
 * variables. The preference variables are saved into a global preference file.
 * Preference variables are static variables which can be accessed from any
 * plugin class. Values saved in this global file, can be accessed also after a
 * JOSM restart.
 * 
 * @author Beata
 * @version $Revision$
 */
public final class PrefManager {
    
    private static final PrefManager UNIQUE_INSTANCE = new PrefManager();
    
    
    /**
     * Returns the unique instance of the {@code PrefManager}.
     * 
     * @return a {@code PrefManager}
     */
    public static PrefManager getInstance() {
        return UNIQUE_INSTANCE;
    }
    
    /**
     * Saves the given value to the global preference file. Based on this value
     * an occurred error is shown or not to the end user.
     * 
     * @param value a boolean value
     */
    public void saveSupressErrorFlag(boolean value) {
        Main.pref.put(Keys.ERROR_SUPRESS, value);
    }
    
    /**
     * Loads the suppress error flag.
     * 
     * @return a boolean value
     */
    public boolean loadSupressErrorFlag() {
        return Main.pref.getBoolean(Keys.ERROR_SUPRESS);
    }
}