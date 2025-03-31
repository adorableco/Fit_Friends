/** @format */

import React from "react";
import { View, Text, StyleSheet } from "react-native";
import formatDateTime from "../FormatDateTime";

const categoryEmojis = {
  soccer: "⚽",
  basketball: "🏀",
  badminton: "🏸",
  running: "🏃",
  default: "🏃",
};

const MyMatchList = ({ detail }) => {
  return (
    <View style={styles.matchItem}>
      <View style={styles.matchInfo}>
        <Text style={styles.matchTitle}>{detail.title}</Text>
        <View style={styles.detailRow}>
          <Text style={styles.categoryEmoji}>
            {categoryEmojis[detail.category] || categoryEmojis.default}
          </Text>
          <View style={styles.detailColumn}>
            <Text style={styles.matchDetail}>{detail.place}</Text>
            <Text style={styles.matchDetail}>
              {formatDateTime(detail.startTime)}
            </Text>
          </View>
        </View>
      </View>
      <View
        style={[
          styles.statusBadge,
          detail.currentHeadCnt === detail.headCnt
            ? styles.statusFull
            : styles.statusOpen,
        ]}
      >
        <Text
          style={[
            styles.statusText,
            detail.currentHeadCnt === detail.headCnt && styles.statusFullText,
          ]}
        >
          {detail.currentHeadCnt === detail.headCnt ? "모집 완료" : "모집 중"}
        </Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  matchItem: {
    backgroundColor: "#fff",
    padding: 16,
    borderRadius: 12,
    marginBottom: 12,
    flexDirection: "row",
    justifyContent: "space-between",
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
  matchInfo: {
    flex: 1,
    marginRight: 12,
  },
  matchTitle: {
    fontSize: 16,
    fontWeight: "600",
    color: "#333",
    marginBottom: 8,
  },
  detailRow: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "center",
    marginBottom: 4,
  },
  detailColumn: {
    flex: 1,
    marginLeft: 8,
  },
  categoryEmoji: {
    fontSize: 42,
  },
  matchDetail: {
    fontSize: 13,
    color: "#666",
  },
  statusBadge: {
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 16,
    minWidth: 80,
    alignItems: "center",
  },
  statusOpen: {
    backgroundColor: "#E8F5E9",
  },
  statusFull: {
    backgroundColor: "#F5F5F5",
  },
  statusText: {
    fontSize: 12,
    fontWeight: "500",
    color: "#4CAF50",
  },
  statusFullText: {
    color: "#666",
  },
});

export default MyMatchList;
