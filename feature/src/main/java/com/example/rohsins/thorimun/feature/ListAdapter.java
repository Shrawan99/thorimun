package com.example.rohsins.thorimun.feature;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> noticeHeader;
    private HashMap<String, List<String>> noticeBody;

    public ListAdapter(Context context, List<String> noticeHeader, HashMap<String, List<String>> noticeBody) {
        this.context = context;
        this.noticeHeader = noticeHeader;
        this.noticeBody = noticeBody;
    }

    @Override
    public int getGroupCount() {
        return this.noticeHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.noticeBody.get(this.noticeHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.noticeHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.noticeBody.get(this.noticeHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.notice_header, null);
        }

        TextView textViewNoticeHeader = (TextView) convertView.findViewById(R.id.noticeHeader);
        textViewNoticeHeader.setTypeface(null, Typeface.BOLD);
        textViewNoticeHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.notice_body, null);
        }

        TextView textViewNoticeBody = (TextView) convertView.findViewById(R.id.noticeBody);
        textViewNoticeBody.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
