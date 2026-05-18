import type { ProfileResponse, SimilarProfileResponse } from "../types/api";

type Props = {
  profile?: ProfileResponse;
  similarProfiles: SimilarProfileResponse[];
};

export default function ProfilePanel({ profile, similarProfiles }: Props) {
  if (!profile) {
    return (
      <div className="card">
        <h3>プロフィール詳細</h3>
        <p className="muted">社員を選択してください</p>
      </div>
    );
  }

  return (
    <div className="card">
      <h3>プロフィール詳細</h3>

      <div className="profile-header">
        <div className="avatar">{profile.name.slice(0, 1)}</div>
        <div>
          <h2>{profile.name}</h2>
          <p>{profile.hrid}</p>
          <p>{profile.mailAddress}</p>
          <span className="badge">{profile.rankName}</span>
        </div>
      </div>

      <p className="free-text">{profile.free}</p>

      <h4>類似ユーザー</h4>
      <div className="similar-list">
        {similarProfiles.map((p) => (
          <div key={p.hrid} className="similar-item">
            <strong>{p.name}</strong>
            <span>{Math.round(p.similarity * 100)}%</span>
            <small>{p.commonSkillNames}</small>
          </div>
        ))}
      </div>
    </div>
  );
}