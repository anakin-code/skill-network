import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { fetchUsers, login } from "../api/userApi";
import type { UserResponse } from "../types/user";
import { buttonStyle, formStyle, inputStyle } from "../styles/commonStyles";

function LoginPage() {
  const navigate = useNavigate();

  const [users, setUsers] = useState<UserResponse[]>([]);
  const [email, setEmail] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const loadUsers = async () => {
      try {
        const data = await fetchUsers();
        setUsers(data);
      } catch (error) {
        if (error instanceof Error) {
          setErrorMessage(error.message);
        }
      }
    };

    loadUsers();
  }, []);

  const loginHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage("");

    if (email === "") {
      setErrorMessage("ユーザーを選択してください。");
      return;
    }

    try {
      const user = await login(email);

      localStorage.setItem("userId", String(user.id));
      localStorage.setItem("userKey", user.userKey);
      localStorage.setItem("displayName", user.displayName);
      localStorage.setItem("homepageKey", user.homepageKey);

      navigate("/questions");
      window.location.reload();
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      }
    }
  };

  return (
    <div>
      <h2>ログイン</h2>

      <form onSubmit={loginHandler} style={formStyle}>
        <select
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          style={inputStyle}
        >
          <option value="">ユーザーを選択</option>

          {users.map((user) => (
            <option key={user.id} value={user.email}>
              {user.displayName}（{user.email}）
            </option>
          ))}
        </select>

        {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

        <button type="submit" style={buttonStyle}>
          ログイン
        </button>
      </form>
    </div>
  );
}

export default LoginPage;