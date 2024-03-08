/** @format */

import axios from "axios";
import React, { useEffect, useState } from "react";
import { Text, StyleSheet, View } from "react-native";
import MyMatchList from "./MyMatchList";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Modal from "react-modal";

export default function UserDetailScreen({ route, navigation }) {
  const [userDetail, setUserDetail] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const [isModifying, setIsModifying] = useState(false);
  const [genderVisible, setGenderVisible] = useState(true);
  const [ageVisible, setAgeVisible] = useState(true);
  const [changeName, setChangeName] = useState("");
  // const userId = route.params.userId;

  const onChangeName = (e) => {
    setChangeName(e.target.value);
  };

  const onClickLogout = async () => {
    await AsyncStorage.removeItem("@accessToken");
    await AsyncStorage.removeItem("@userId");
    alert("로그아웃이 완료되었습니다.");
    navigation.navigate("HomeScreen");
  };

  const onClickModifyBtn = async () => {
    const token = await AsyncStorage.getItem("@accessToken");
    await axios
      .put(
        "http://localhost:8080/api/user",
        {
          name: changeName,
          ageVisible: ageVisible,
          genderVisible: genderVisible,
        },
        {
          headers: {
            "Access-Control-Allow-Origin": "http://localhost:19006",
            Authorization: token,
          },
        },
      )
      .then((res) => {
        setIsModifying(false);
        console.log(res.data);
      });
  };

  useEffect(() => {
    const fetchData = async () => {
      const token = await AsyncStorage.getItem("@accessToken");
      console.log(token);

      const userId = await AsyncStorage.getItem("@userId");

      await axios
        .get(`http://localhost:8080/api/user/${userId}`, {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          setIsLoading(false);
          console.log("res.data=", res.data);
          setUserDetail(res.data);
        })
        .catch((e) => console.log(e));
    };
    fetchData();
  }, [isLoading]);

  if (isLoading) {
    return (
      <View>
        <Text>로딩중...</Text>
      </View>
    );
  }

  return (
    <View style={styles.view}>
      <img src={userDetail.picture} style={styles.img} />
      <Text style={styles.level}>{userDetail.level}</Text>
      <Text style={styles.name}>{userDetail.name}</Text>
      {userDetail.isMyDetail && (
        <Text style={{ marginTop: 10 }}>{userDetail.email}</Text>
      )}
      <Text style={styles.detail}>
        {userDetail.gender == "M"
          ? "남"
          : userDetail.gender == "F"
          ? "여"
          : "성별 비공개"}{" "}
        /{" "}
        {userDetail.age == "youngs"
          ? "어린이"
          : userDetail.age == "teens"
          ? "청소년"
          : userDetail.age == "adults"
          ? "성인"
          : "나이대 비공개"}{" "}
      </Text>
      <View
        style={{
          display: "flex",
          flexDirection: "row",
        }}
      >
        <Modal isOpen={isModifying} style={styles.modal}>
          <h2 style={{ color: "#4CAF50" }}>회원 정보 수정</h2>
          <input
            style={styles.input}
            onChange={onChangeName}
            placeholder='변경할 닉네임'
          />

          <Text style={styles.fileInput}>
            변경할 프로필 이미지
            <input type='file' accept='image/*' placeholder='변경할 이미지' />
          </Text>
          <label style={styles.checkboxLabel}>
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
          <label style={styles.checkboxLabel}>
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

          <button style={styles.btn} onClick={onClickModifyBtn}>
            정보 수정하기
          </button>
        </Modal>
        <button
          style={styles.btn}
          onClick={() => {
            setIsModifying(!isModifying);
          }}
        >
          회원 정보 수정
        </button>
        <button style={styles.btn} onClick={onClickLogout}>
          로그아웃
        </button>
      </View>
      <Text style={styles.winningRate}>
        최근 10회 승률{" "}
        <Text style={styles.rate}>{userDetail.winningRate}%</Text>
      </Text>
      <Text style={styles.winningRate}>
        최근 10회 출석률{" "}
        <Text style={styles.rate}>
          {userDetail.attendanceRate == "Infinity"
            ? "경기 진행 전무"
            : userDetail.attendanceRate}
          %
        </Text>
      </Text>
      {userDetail.isMyDetail &&
        userDetail.participationList.map((participation) => (
          <MyMatchList detail={participation} />
        ))}
    </View>
  );
}

const styles = StyleSheet.create({
  view: {
    justifyItem: "space-around",
    alignItems: "center",
  },
  level: {
    marginTop: 15,
    marginBottom: 10,
    backgroundColor: "#2196F3",
    borderRadius: 30,
    width: "max-content",
    padding: 10,
    color: "white",
  },
  img: {
    width: 110,
    height: 110,
    borderRadius: 90,
    marginTop: 50,
  },
  name: {
    fontWeight: 700,
    fontSize: 30,
    lineHeight: 31,
    textAlign: "center",
    color: "#000000",
  },
  detail: {
    fontWeight: 300,
    fontSize: 16,
    lineHeight: 18,
    textAlign: "center",
    color: "#000000",
    marginBottom: 10,
    marginTop: 10,
  },
  winningRate: {
    marginTop: 20,
    fontWeight: 700,
    fontSize: 16,
    lineHeight: 21,
    textAlign: "center",
    color: "#000000",
  },
  rate: {
    color: "#4CAF50",
    fontWeight: 700,
  },
  btn: {
    fontWeight: 600,
    marginTop: 5,
    marginRight: 10,
    marginBottom: 10,
    width: 100,
    height: 40,
    backgroundColor: "#4CAF50",
    borderRadius: 50,
    border: "none",
    color: "white",
  },
  modal: {
    overlay: {
      backgroundColor: "rgba(0, 0, 0, 0.5)",
    },
    content: {
      top: 200,
      height: 300,
      display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      alignItems: "center",
      padding: 20,
      borderRadius: 10,
      backgroundColor: "white",
    },
  },
  input: {
    marginBottom: 10,
    width: "150px",
    height: "36px",
    fontSize: 15,

    backgroundColor: "#4CAF50",
    color: "white",
    border: "none",
    borderRadius: 50,
  },
  fileInput: {
    marginBottom: 10,
  },
  checkboxLabel: {
    marginBottom: 10,
  },
  confirmButton: {
    marginTop: 10,
  },
});
