package io.github.ynagarjuna1995.udacitynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    /**
     * Constant value for the article loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int ARTICLE_LOADER_ID = 1;
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search?q=technology&api-key=test";
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressBar;
    /**
     * Adapter for the list of articles
     */
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);


        ListView newsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);
        
        mNewsAdapter = new NewsAdapter(this, new ArrayList<Article>());

        newsListView.setAdapter(mNewsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Article article = mNewsAdapter.getItem(position);
                String url = article.getArticleLink();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        //connection check
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }
    /*Inherited Methods*/
    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        return new ArticleLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        mProgressBar.setVisibility(View.GONE);

        // Set empty state text to display "No articles found."
        mEmptyStateTextView.setText(R.string.no_articles);
        mNewsAdapter.clear();

        /*If there is valid response list get updated*/
        if (articles != null && !articles.isEmpty()) {
            mNewsAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

        mNewsAdapter.clear();
    }
}
