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

import java.util.Calendar;


/**
 * Defines the week day names.
 * 
 * @author Beata
 * @version $Revision$
 */
enum DayName {
    
    MON {
        
        @Override
        public String toString() {
            return "Mon";
        }
    },
    TUE {
        
        @Override
        public String toString() {
            return "Tue";
        }
    },
    WED {
        
        @Override
        public String toString() {
            return "Wed";
        }
    },
    THR {
        
        @Override
        public String toString() {
            return "Thu";
        }
    },
    FRI {
        
        @Override
        public String toString() {
            return "Fri";
        }
    },
    SAT {
        
        @Override
        public String toString() {
            return "Sat";
        }
    },
    SUN {
        
        @Override
        public String toString() {
            return "Sun";
        }
    };
    
    /**
     * Returns the week day name corresponding to the given day of week.
     * 
     * @param dayOfWeek represents the number of day from a week
     * @return a {@code DayName} object
     */
    static DayName getDayName(int dayOfWeek) {
        DayName result;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                result = MON;
                break;
            case Calendar.TUESDAY:
                result = TUE;
                break;
            case Calendar.WEDNESDAY:
                result = WED;
                break;
            case Calendar.THURSDAY:
                result = THR;
                break;
            case Calendar.FRIDAY:
                result = FRI;
                break;
            case Calendar.SATURDAY:
                result = SAT;
                break;
            default:
                result = SUN;
                break;
        }
        return result;
    }
}