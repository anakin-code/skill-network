import type { LoginUser, UserResponse } from "../types/user";

const API_BASE = "http://localhost:8080";

export async function fetchUsers(): Promise<UserResponse[]> {
  const response = await fetch(`${API_BASE}/api/users`);

  if (!response.ok) {
    throw new Error("ユーザー一覧の取得に失敗しました。");
  }

  return await response.json();
}

export async function login(email: string): Promise<LoginUser> {
  const response = await fetch(`${API_BASE}/api/users/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email }),
  });

  if (!response.ok) {
    throw new Error("ログインに失敗しました。");
  }

  return await response.json();
}

export async function registerUser(
  realName: string,
  email: string,
  displayName: string
): Promise<LoginUser> {
  const response = await fetch(`${API_BASE}/api/users`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ realName, email, displayName }),
  });

  if (!response.ok) {
    throw new Error("ユーザー登録に失敗しました。");
  }

  return await response.json();
}