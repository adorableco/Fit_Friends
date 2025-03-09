/** @format */

import axios from "axios";
import React, { useState } from "react";
import { Text, StyleSheet, Image, View } from "react-native";
import { EXPO_PUBLIC_API_URL } from "@env";

const SignUpScreen = ({ route, navigation }) => {
  const [name, setName] = useState(route.params.userData.name);
  const [email, setEmail] = useState(route.params.userData.email);
  const [picture, setPicture] = useState(route.params.userData.picture);
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("M");
  const [genderVisible, setGenderVisible] = useState(true);
  const [ageVisible, setAgeVisible] = useState(true);
  const API_URL = EXPO_PUBLIC_API_URL;

  const signUp = async () => {
    await axios
      .post(`${API_URL}/user-service/signup`, {
        name: name,
        email: email,
        picture: picture,
        gender: gender,
        age: age,
        level: "beginner",
        genderVisible: genderVisible,
        ageVisible: ageVisible,
      })
      .then(async (res) => {
        console.log(res.data);
      });
  };

  const onChangeName = (e) => {
    setName(e.target.value);
  };

  const onChangeGender = (e) => {
    setGender(e.target.value);
    console.log(gender);
  };

  const onChangeAge = (e) => {
    setAge(e.target.value);
  };

  return (
    <View style={styles.container}>
      <h2 style={styles.h2}>회원 가입</h2>
      <Text>
        프로필 사진 URL: <img src={picture} width={"75px"} height={"75px"} />
      </Text>
      <button style={styles.imgBtn}>이미지 변경</button>
      <label>
        이름{" "}
        <input
          style={styles.content}
          placeholder={name}
          onChange={(e) => onChangeName(e)}
        />
      </label>
      <label>
        이메일 <Text style={styles.content}>{email}</Text>
      </label>

      <label>
        성별{" "}
        <select onChange={onChangeGender}>
          <option value='M'>남</option>
          <option value='F'>여</option>
        </select>
      </label>

      <label>
        성별 공개
        <input
          type='checkbox'
          checked={genderVisible}
          onChange={() => {
            setGenderVisible(!genderVisible);
          }}
          name='genderVisible'
        />
      </label>
      <label>
        나이대{" "}
        <select onChange={onChangeAge}>
          <option value='youngs'>어린이...?</option>
          <option value='teens'>청소년</option>
          <option value='adults'>성인</option>
        </select>
      </label>

      <label>
        나이대 공개
        <input
          type='checkbox'
          name='ageVisible'
          checked={ageVisible}
          onChange={() => {
            setAgeVisible(!ageVisible);
          }}
        />
      </label>
      <button style={styles.signUpBtn} onClick={signUp}>
        회원 가입
      </button>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "center",
  },
  form: {
    display: "flex",
    flexDirection: "column",
    margin: 20,
  },
  content: {
    width: "214px",
    height: "43px",
    backgroundColor: "#4CAF50",
    color: "FFFFFF",
    border: "none",
    borderRadius: 50,
  },
  h2: {
    color: "#4CAF50",
    fontSize: 50,
  },
  imgBtn: {
    width: 92,
    height: 37,
    border: "1px solid #4CAF50",
    borderRadius: 50,
    color: "#4CAF50",
  },

  imageStyle: {
    marginTop: 217,
    paddingLeft: 37,
    width: 75,
    height: 75,
    borderRadius: 100,
    backgroundColor: "#4CAF50",
    //justifyContent: 'center',
    //alignItems: 'center',
    //flexDirection: 'row',
    flexShrink: 0,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  buttonStyle: {
    width: 92,
    height: 37,
    borderRadius: 50,
    backgroundColor: "#FFFFFF",
    borderWidth: 1,
    borderColor: "#4CAF50",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "row",
    flexShrink: 0,
  },
  buttonTextStyle: {
    color: "#FFFFFF",
    fontFamily: "Roboto",
    fontSize: 16,
    fontWeight: "400",
    lineHeight: 20,
    flexShrink: 0,
  },
  signUpBtn: {
    width: 117,
    height: 43,
    backgroundColor: "#4CAF50",
    borderRadius: 50,
    border: "none",
  },
});

export default SignUpScreen;
