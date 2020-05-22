/*
Whack-A-Pede
David Clark
Development Portfolio 6
MDV469-O, C202005-01
*/

package com.twilightcitizen.whackapede.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.twilightcitizen.whackapede.R;
import com.twilightcitizen.whackapede.activities.GameActivity;
import com.twilightcitizen.whackapede.utilities.LeaderboardEntry;
import com.twilightcitizen.whackapede.utilities.LeaderboardUtility;
import com.twilightcitizen.whackapede.utilities.TimeUtility;

import java.util.ArrayList;
import java.util.Locale;

/*
Leaderboard Fragment displays a scrollable list of players' published top scores, ordered from
highest to lowest, using the action bar for back/up navigation.
*/
@SuppressWarnings( "WeakerAccess" ) public class LeaderboardFragment extends Fragment
    implements LeaderboardUtility.OnGetLeadersListener {

    // RecyclerView Adapter for a Leaderboard entry, using a Leaderboard entry ViewHolder.
    private class LeaderAdapter extends RecyclerView.Adapter< LeaderAdapter.LeaderViewHolder > {
        // ViewHolder for a leaderboard entry.
        class LeaderViewHolder extends RecyclerView.ViewHolder {
            // Leader's score and name.
            final ImageView imageAvatar;
            final TextView textDisplayName;
            final TextView textFinalScore;
            final TextView textRoundsInTime;

            LeaderViewHolder( @NonNull View itemView ) {
                super( itemView );

                imageAvatar = itemView.findViewById( R.id.image_avatar );
                textDisplayName = itemView.findViewById( R.id.text_display_name );
                textFinalScore = itemView.findViewById( R.id.text_final_score );
                textRoundsInTime = itemView.findViewById( R.id.text_rounds_in_time );
            }
        }

        // Leaderboard entries to be adapted for view.
        private final ArrayList< LeaderboardEntry > leaderboardEntries;

        LeaderAdapter( ArrayList< LeaderboardEntry > leaderBoardEntries ) {
            this.leaderboardEntries = leaderBoardEntries;
        }

        // View type is determined by placement or position.
        @Override public int getItemViewType( int position ) { return position; }

        @NonNull @Override public LeaderViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
        ) {

            View leaderboardItem = LayoutInflater
                .from( parent.getContext() ).inflate( R.layout.entry_leaderboard, parent, false );

            return new LeaderViewHolder( leaderboardItem );
        }

        @Override public void onBindViewHolder( @NonNull LeaderViewHolder holder, int position ) {
            // All leaderboard items can be bound with the same view holder.
            LeaderboardEntry leaderBoardEntry = leaderboardEntries.get( position );

            holder.textDisplayName.setText( leaderBoardEntry.getDisplayName() );

            holder.textFinalScore.setText( getResources().getQuantityString(
                R.plurals.points,  leaderBoardEntry.getFinalScore(), leaderBoardEntry.getFinalScore()
            ) );

            String roundsInTime = getResources().getQuantityString(
                R.plurals.rounds, leaderBoardEntry.getTotalRounds(), leaderBoardEntry.getTotalRounds()
            );

            roundsInTime += String.format( Locale.getDefault(), getString( R.string.in_time ),
                TimeUtility.getInstance().millisToMinutesAndSeconds(
                    leaderBoardEntry.getTotalTime()
            ) );

            holder.textRoundsInTime.setText( roundsInTime );

            Glide.with( gameActivity ).load( Uri.parse( leaderBoardEntry.getAvatar() ) )
                .placeholder( R.drawable.icon_guest_account ).into( holder.imageAvatar );
        }

        @Override public int getItemCount() { return leaderboardEntries.size(); }
    }

    // Game Activity must host Leaderboard Fragment.
    private GameActivity gameActivity;

    // Check the host context on attachment.
    @Override public void onAttach( @NonNull Context context ) {
        super.onAttach( context );
        checkGameActivityHost( context );
    }

    // Ensure that the host context is a Game Activity.
    private void checkGameActivityHost( Context context ) {
        if( ! ( context instanceof GameActivity ) )
            throw new ClassCastException( "GameActivity must host LeaderboardFragment" );

        gameActivity = (GameActivity) context;
    }

    // Just inflate the view on view creation.
    @Override public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate( R.layout.fragment_leaderboard, container, false );
    }

    // Show the action bar on resume.
    @Override public void onResume() {
        super.onResume();
        gameActivity.showActionBar();
    }

    // Setup the leaderboard after the view is created.
    @Override public void onViewCreated(
        @NonNull View view, @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated( view, savedInstanceState );
        LeaderboardUtility.getInstance().getTopLimitLeaders( gameActivity, 100, this );
    }

    @Override public void onGetLeaderSucceeded( ArrayList< LeaderboardEntry > topLimitLeaders ) {
        View view = getView();

        if( view == null ) return;

        ConstraintLayout layoutRetrieving = view.findViewById( R.id.layout_retrieving );
        ConstraintLayout layoutNoLeaders = view.findViewById( R.id.layout_no_leaders );

        boolean noLimitLeaders = topLimitLeaders.size() < 1;

        layoutRetrieving.setVisibility( View.GONE );
        layoutNoLeaders.setVisibility( noLimitLeaders ? View.VISIBLE : View.GONE );

        // Guard against setting up an empty recycler view.
        if( noLimitLeaders ) return;

        RecyclerView recycleLeaders = view.findViewById( R.id.recycler_leaderboard );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( gameActivity );
        RecyclerView.Adapter adapterLeaders = new LeaderAdapter( topLimitLeaders );

        recycleLeaders.setHasFixedSize( true );
        recycleLeaders.setLayoutManager( layoutManager );
        recycleLeaders.setAdapter( adapterLeaders );
    }

    @Override public void onGetLeadersFailed( Exception e ) {
        View view = getView();

        if( view == null ) return;

        ConstraintLayout layoutRetrieving = view.findViewById( R.id.layout_retrieving );
        ConstraintLayout layoutNoLeaders = view.findViewById( R.id.layout_no_leaders );

        layoutRetrieving.setVisibility( View.GONE );
        layoutNoLeaders.setVisibility( View.VISIBLE );
    }
}