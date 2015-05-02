package pro.dbro.ble.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.delight.android.identicons.SymmetricIdenticon;
import pro.dbro.ble.R;
import pro.dbro.ble.data.DataStore;
import pro.dbro.ble.data.model.Peer;
import pro.dbro.ble.ui.adapter.MessageAdapter;

/**
 * A Fragment that displays all messages from a particular peer
 */
public class ProfileFragment extends Fragment {

    DataStore mDataStore;
    RecyclerView mRecyclerView;
    MessageAdapter mAdapter;
    Peer mFromPeer;

    public static ProfileFragment createForPeer(@NonNull DataStore dataStore,
                                                @NonNull Peer peer) {

        ProfileFragment frag = new ProfileFragment();
        frag.setFromPeer(peer);
        frag.setDataStore(dataStore);
        return frag;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void setFromPeer(Peer fromPeer) {
        mFromPeer = fromPeer;
    }

    public void setDataStore(DataStore dataStore) {
        mDataStore = dataStore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mDataStore == null)
            throw new IllegalStateException("MessageListFragment must be equipped with a DataStore. Did you call #setDataStore");

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_peer_profile, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MessageAdapter(getActivity(), mFromPeer, mDataStore, null, MessageAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mRecyclerView.setAdapter(mAdapter);

        ((SymmetricIdenticon) root.findViewById(R.id.profile_identicon)).show(new String(mFromPeer.getPublicKey()));
        ((TextView) root.findViewById(R.id.profile_name)).setText(mFromPeer.getAlias());

        return root;
    }
}
