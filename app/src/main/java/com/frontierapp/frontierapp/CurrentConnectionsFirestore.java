/**
 *
 */
package com.frontierapp.frontierapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentConnectionsFirestore extends UserFirestore{
    private static final String TAG = "CurrentPartnersFS";

    private NotificationFirestore notificationFirestore = new NotificationFirestore();

    public CurrentConnectionsFirestore(Context context) {
        super(context);
    }

    public Boolean initiateNewPartnershipToFirestore(final String currentUserID, final String newPartnerId,
                                                     final PartnerStatus status){
        Log.d(TAG, "addNewPartnerToFirestore() called with: currentUserID = ["
                + currentUserID + "], newPartnerId = [" + newPartnerId + "]");
        final DocumentReference connectionsUserData = userDatabase.document(newPartnerId);
        connectionsUserData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final String first_name = documentSnapshot.getString(FIRST_NAME);
                        final String last_name = documentSnapshot.getString(LAST_NAME);
                        final String profile_url = documentSnapshot.getString(PROFILE_AVATAR);
                        final String title = documentSnapshot.getString(PROFILE_TITLE);

                        final Map<String, Object> partnered = new HashMap<>();
                        partnered.put("partner", status.getStatus());
                        partnered.put(CONNECTIONS_FIRST_NAME, first_name);
                        partnered.put(CONNECTIONS_LAST_NAME, last_name);
                        partnered.put(CONNECTIONS_PROFILE_URL, profile_url);
                        partnered.put(CONNECTIONS_TITLE, title);

                        final DocumentReference userConnections = userData.collection("Connections").document(newPartnerId);
                        Log.i(TAG, "addNewPartnerToFirestore: userConnections = "
                                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                                        + documentSnapshot.exists() + "]");
                                if(documentSnapshot.exists()){
                                    userConnections
                                            .update(partnered)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    sendRequestToNewPartnerToFirestore(currentUserID, newPartnerId, status);
                                                    Log.i(TAG, "onSuccess: true");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "onFailure: ", e);
                                                }
                                            });
                                }else{
                                    partnered.put("favorite", false);
                                    partnered.put("follower", false);
                                    partnered.put("timestamp", FieldValue.serverTimestamp());
                                    userConnections
                                            .set(partnered)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    sendRequestToNewPartnerToFirestore(currentUserID, newPartnerId, status);
                                                    Log.i(TAG, "onSuccess: true");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "onFailure: ", e);
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "onFailure: ", e);
                            }
                        }));
                    }
                });


        return true;
    }

    public Boolean sendRequestToNewPartnerToFirestore(final String currentUserID, final String newPartnerId,
                                                      final PartnerStatus status){
        Log.d(TAG, "sendRequestToNewPartnerToFirestore() called with: currentUserID = [" + currentUserID + "], " +
                "newPartnerId = [" + newPartnerId + "], status = [" + status + "]");
        final DocumentReference responderUserData = userDatabase.document(newPartnerId);

        userData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final String first_name = documentSnapshot.getString(FIRST_NAME);
                        final String last_name = documentSnapshot.getString(LAST_NAME);
                        final String profile_url = documentSnapshot.getString(PROFILE_AVATAR);
                        final String title = documentSnapshot.getString(PROFILE_TITLE);

                        final Map<String, Object> partnered = new HashMap<>();
                        partnered.put(CONNECTIONS_FIRST_NAME, first_name);
                        partnered.put(CONNECTIONS_LAST_NAME, last_name);
                        partnered.put(CONNECTIONS_PROFILE_URL, profile_url);
                        partnered.put(CONNECTIONS_TITLE, title);

                        if(status.equals(PartnerStatus.Pending_Sent)) {
                            notificationFirestore.sendNotification(NotificationType.PARTNERSHIP_REQUEST, currentUserID, newPartnerId);
                            partnered.put("partner", PartnerStatus.Pending_Response.getStatus());
                        }
                        else {
                            if(status.equals(PartnerStatus.True))
                                notificationFirestore.sendNotification(NotificationType.PARTNERSHIP_ACCEPTED, currentUserID, newPartnerId);
                            partnered.put("partner", status.getStatus());
                        }

                        final DocumentReference userConnections = responderUserData.collection("Connections").document(currentUserID);

                        Log.i(TAG, "addNewPartnerToFirestore: userConnections = "
                                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                                        + documentSnapshot.exists() + "]");
                                if(documentSnapshot.exists()){
                                    userConnections
                                            .set(partnered, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i(TAG, "onSuccess: true");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "onFailure: ", e);
                                                }
                                            });
                                }else{
                                    partnered.put("favorite", false);
                                    partnered.put("follower", false);
                                    partnered.put("timestamp", FieldValue.serverTimestamp());
                                    userConnections
                                            .set(partnered)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i(TAG, "onSuccess: true");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "onFailure: ", e);
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "onFailure: ", e);
                            }
                        }));

                    }
                });



        return true;
    }

    public Boolean addFavoriteIdToFirestore(final String currentUserID, final String newFavId){
        final DocumentReference favUserData = userDatabase.document(newFavId);

        favUserData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final Map<String, Object> favorited = new HashMap<>();
                        String first_name = documentSnapshot.getString(FIRST_NAME);
                        String last_name = documentSnapshot.getString(LAST_NAME);
                        String profile_url = documentSnapshot.getString(PROFILE_AVATAR);
                        String title = documentSnapshot.getString(PROFILE_TITLE);

                        favorited.put("favorite", true);
                        favorited.put(CONNECTIONS_FIRST_NAME, first_name);
                        favorited.put(CONNECTIONS_LAST_NAME, last_name);
                        favorited.put(CONNECTIONS_PROFILE_URL, profile_url);
                        favorited.put(CONNECTIONS_TITLE, title);

                        final DocumentReference userConnections = userData.collection("Connections").document(newFavId);
                        Log.i(TAG, "addFavoriteIdToFirestore: userConnections = "
                                + userConnections
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                                        + documentSnapshot.exists() + "]");
                                if(documentSnapshot.exists()){
                                    userConnections
                                            .set(favorited, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    addFollowerIdToFirestore(currentUserID, newFavId);
                                                    notificationFirestore.sendNotification(NotificationType.FOLLOW, currentUserID, newFavId);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "onFailure: ", e);
                                                }
                                            });
                                }else{
                                    favorited.put("partner", "False");
                                    favorited.put("follower", false);
                                    favorited.put("timestamp", FieldValue.serverTimestamp());
                                    userConnections
                                            .set(favorited)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    addFollowerIdToFirestore(currentUserID, newFavId);
                                                    notificationFirestore.sendNotification(NotificationType.FOLLOW, currentUserID, newFavId);

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "onFailure: ", e);
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "onFailure: ", e);
                            }
                        }));
                    }
                });



        return true;
    }

    public Boolean removeFavoriteIdFromFirestore(final String currentUserID, final String removeFavId){
        userData.collection("Connections").document(removeFavId)
                .update("favorite", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFollowerIdFromFirestore(currentUserID, removeFavId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        });

        return true;
    }

    public Boolean addFollowerIdToFirestore(final String currentUserID, final String currentFavID){
        Log.d(TAG, "addFollowerIdToFirestore() called with: currentUserID = " +
                "[" + currentUserID + "], currentFavID = [" + currentFavID + "]");
        final DocumentReference favUserData = userDatabase.document(currentFavID);

        userData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String first_name = documentSnapshot.getString(FIRST_NAME);
                        String last_name = documentSnapshot.getString(LAST_NAME);
                        String profile_url = documentSnapshot.getString(PROFILE_AVATAR);
                        String title = documentSnapshot.getString(PROFILE_TITLE);

                        final Map<String, Object> followed = new HashMap<>();
                        followed.put("follower", true);
                        followed.put(CONNECTIONS_FIRST_NAME, first_name);
                        followed.put(CONNECTIONS_LAST_NAME, last_name);
                        followed.put(CONNECTIONS_PROFILE_URL, profile_url);
                        followed.put(CONNECTIONS_TITLE, title);

                        final DocumentReference userConnections = favUserData.collection("Connections").document(currentUserID);
                        Log.i(TAG, "addFavoriteIdToFirestore: userConnections = "
                                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                                        + documentSnapshot.exists() + "]");
                                if(documentSnapshot.exists()){
                                    userConnections
                                            .set(followed, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i(TAG, "onSuccess: true");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "onFailure: ", e);
                                                }
                                            });
                                }else{
                                    followed.put("partner", "False");
                                    followed.put("favorite", false);
                                    followed.put("timestamp", FieldValue.serverTimestamp());
                                    userConnections
                                            .set(followed)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i(TAG, "onSuccess: true");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "onFailure: ", e);
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "onFailure: ", e);
                            }
                        }));

                        Log.d(TAG, "addFollowerIdToFirestore() returned: " + true);
                    }
                });

        return true;

    }

    public Boolean removeFollowerIdFromFirestore(final String currentUserID, final String currentFavID){
        DocumentReference favUserData = userDatabase.document(currentFavID);

        favUserData.collection("Connections").document(currentUserID)
                .update("follower", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "onSuccess: true");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        });

        return true;
    }

}
