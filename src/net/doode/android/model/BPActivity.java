/**************************************************************************
 *  This file is part of the Doode Android project                        *
 *  Copyright (C) 2012 Eduardo Weiland                                    *
 *  duduweiland@users.sourceforge.net                                     *
 *                                                                        *
 *  This program is free software: you can redistribute it and/or modify  *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  This program is distributed in the hope that it will be useful,       *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>  *
 **************************************************************************/

package net.doode.android.model;

import java.util.Date;

public class BPActivity {

    private int          mID;
    private Date         mDate;
    private String       mContent;
    private BPUser       mBPUser;
    private ActivityType mType;

    public BPActivity(String pContent, BPUser pUser) {
        setContent(pContent);
        setUser(pUser);
    }

    public enum ActivityType {
        activity_update,
        new_forum_topic,
        new_forum_post,
        created_group,
        joined_group,
        friendship_accepted,
        friendship_created,
        new_member
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public BPUser getUser() {
        return mBPUser;
    }

    public void setUser(BPUser pUser) {
        mBPUser = pUser;
    }

    public ActivityType getType() {
        return mType;
    }

    public void setType(ActivityType type) {
        mType = type;
    }
}
