/** @format */

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyC4iOUbh4c3bxW8_DTWYIdQVtQc2qqk-Bc",
  authDomain: "fitfriends-cf082.firebaseapp.com",
  projectId: "fitfriends-cf082",
  storageBucket: "fitfriends-cf082.appspot.com",
  messagingSenderId: "660940125943",
  appId: "1:660940125943:web:122d728129ac500e3f85f2",
};
// Initialize Firebase
const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);
