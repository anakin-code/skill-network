import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { registerUser } from "../api/userApi";
import { buttonStyle, formStyle, inputStyle } from "../styles/commonStyles";

function UserRegisterPage() {
  const navigate = useNavigate();

  const [realName, setRealName] = useState("");
  const [email, setEmail] = useState("");
  const [displayName, setDisplayName] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const registerHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage("");

    if (realName.trim() === "") {
      setErrorMessage("本名を入力してください。");
      return;
    }

    if (email.trim() === "") {
      setErrorMessage("メールアドレスを入力してください。");
      return;
    }

    if (displayName.trim() === "") {
      setErrorMessage("表示名を入力してください。");
      return;
    }

    try {
      const user = await registerUser(realName, email, displayName);

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
      <h2>ユーザー登録</h2>

      <form onSubmit={registerHandler} style={formStyle}>
        <input
          value={realName}
          onChange={(event) => setRealName(event.target.value)}
          placeholder="本名"
          style={inputStyle}
        />

        <input
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          placeholder="メールアドレス"
          style={inputStyle}
        />

        <input
          value={displayName}
          onChange={(event) => setDisplayName(event.target.value)}
          placeholder="表示名 例：匿名ユーザーA"
          style={inputStyle}
        />

        {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

        <button type="submit" style={buttonStyle}>
          登録
        </button>
      </form>
    </div>
  );
}

export default UserRegisterPage;