package io.github.ynagarjuna1995.udacitynews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

class ViewHolder {
    TextView title;
    TextView sectionName;
}

public class NewsAdapter extends ArrayAdapter<Article> {

    public NewsAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            // inflate the layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            // set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.article_title);
            viewHolder.sectionName = (TextView) convertView.findViewById(R.id.section_name);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // object item based on the position
        Article currentArticle = getItem(position);

        // assign values if the object is not null
        if (currentArticle != null) {
            // get the views from the ViewHolder and then set the values
            viewHolder.title.setText(currentArticle.getTitle());
            viewHolder.sectionName.setText(currentArticle.getSectionName());
        }

        return convertView;
    }
}