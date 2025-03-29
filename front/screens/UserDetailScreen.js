/** @format */

import axios from "axios";
import React, { useEffect, useState } from "react";
import { Text, StyleSheet, View } from "react-native";
import MyMatchList from "./MyMatchList";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Modal from "react-modal";
import { EXPO_PUBLIC_API_URL } from "@env";

export default function UserDetailScreen({ route, navigation }) {
  const [userDetail, setUserDetail] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const [isModifying, setIsModifying] = useState(false);
  const [genderVisible, setGenderVisible] = useState(true);
  const [ageVisible, setAgeVisible] = useState(true);
  const [changeName, setChangeName] = useState("");
  const API_URL = EXPO_PUBLIC_API_URL;

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
        `${API_URL}/user-service/users`,
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
        .get(`${API_URL}/user-service/users/${userId}`, {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          setIsLoading(false);
          console.log("res.data=", res.data.data);
          setUserDetail(res.data.data);
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
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>내 프로필</Text>
      </View>
      <View style={styles.profileSection}>
        <img src={userDetail.picture} style={styles.profileImage} />
        <View style={styles.profileInfo}>
          <Text style={styles.userName}>{userDetail.name}</Text>
          <Text style={styles.userEmail}>{userDetail.email}</Text>
        </View>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>내 통계</Text>
        <View style={styles.statsContainer}>
          <View style={styles.statItem}>
            <Text style={styles.statValue}>{userDetail.level}</Text>
            <Text style={styles.statLabel}>레벨</Text>
          </View>
          <View style={styles.statItem}>
            <Text style={styles.statValue}>{userDetail.winningRate}%</Text>
            <Text style={styles.statLabel}>승률</Text>
          </View>
          <View style={styles.statItem}>
            <Text style={styles.statValue}>
              {userDetail.attendanceRate == "Infinity"
                ? "경기 진행 전무"
                : userDetail.attendanceRate}
              %
            </Text>
            <Text style={styles.statLabel}>출석률</Text>
          </View>
        </View>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>내 정보</Text>
        <Text style={styles.userDetail}>
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
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>참가 기록</Text>
        {userDetail.isMyDetail &&
          userDetail.participationList.map((participation) => (
            <MyMatchList detail={participation} />
          ))}
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>계정 관리</Text>
        <View style={styles.actionButtons}>
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
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f5f5f5",
  },
  header: {
    backgroundColor: "#fff",
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: "#e0e0e0",
    alignItems: "center",
    elevation: 2,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.1,
    shadowRadius: 2,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: "600",
    color: "#333",
  },
  profileSection: {
    backgroundColor: "#fff",
    padding: 20,
    marginTop: 12,
    marginHorizontal: 12,
    borderRadius: 16,
    flexDirection: "row",
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  profileImage: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginRight: 16,
    borderWidth: 2,
    borderColor: "#4CAF50",
  },
  profileInfo: {
    flex: 1,
  },
  userName: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333",
    marginBottom: 4,
  },
  userEmail: {
    fontSize: 14,
    color: "#666",
    marginBottom: 8,
  },
  section: {
    backgroundColor: "#fff",
    padding: 16,
    marginTop: 12,
    marginHorizontal: 12,
    borderRadius: 16,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: "600",
    color: "#333",
    marginBottom: 12,
  },
  statsContainer: {
    flexDirection: "row",
    justifyContent: "space-around",
    paddingVertical: 12,
  },
  statItem: {
    alignItems: "center",
    flex: 1,
  },
  statValue: {
    fontSize: 18,
    fontWeight: "600",
    color: "#4CAF50",
    marginBottom: 4,
  },
  statLabel: {
    fontSize: 13,
    color: "#666",
  },
  matchItem: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: "#f0f0f0",
  },
  matchItemLast: {
    borderBottomWidth: 0,
  },
  matchImage: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginRight: 12,
  },
  matchInfo: {
    flex: 1,
  },
  matchTitle: {
    fontSize: 15,
    fontWeight: "500",
    color: "#333",
    marginBottom: 4,
  },
  matchDetail: {
    fontSize: 13,
    color: "#666",
  },
  emptyText: {
    textAlign: "center",
    color: "#666",
    fontSize: 14,
    marginTop: 20,
  },
  loadingText: {
    textAlign: "center",
    color: "#666",
    fontSize: 14,
    marginTop: 20,
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
  actionButtons: {
    display: "flex",
    flexDirection: "row",
  },
});
