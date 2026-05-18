import { useEffect, useState } from "react";
import { api } from "../api/client";
import SkillGraph from "../components/SkillGraph";
import ProfilePanel from "../components/ProfilePanel";
import CareerPanel from "../components/CareerPanel";
import ExpertPanel from "../components/ExpertPanel";
import PersonalGraph from "../components/PersonalGraph";
import type {
  ExpertResponse,
  PersonalSkillGraphResponse,
  ProfileResponse,
  SimilarProfileResponse,
  SkillGraphResponse,
} from "../types/api";

export default function DashboardPage() {
  const [graph, setGraph] = useState<SkillGraphResponse>({
    nodes: [],
    edges: [],
  });

  const [selectedSkill, setSelectedSkill] = useState("");
  const [experts, setExperts] = useState<ExpertResponse[]>([]);

  const [selectedHrid, setSelectedHrid] = useState("A001");
  const [profile, setProfile] = useState<ProfileResponse>();
  const [personalGraph, setPersonalGraph] =
    useState<PersonalSkillGraphResponse>();
  const [similarProfiles, setSimilarProfiles] = useState<
    SimilarProfileResponse[]
  >([]);

  useEffect(() => {
    api.getSkillGraph().then(setGraph).catch(console.error);
  }, []);

  useEffect(() => {
    api.getProfile(selectedHrid).then(setProfile).catch(console.error);
    api.getPersonalGraph(selectedHrid).then(setPersonalGraph).catch(console.error);
    api
      .getSimilarProfiles(selectedHrid, 5)
      .then(setSimilarProfiles)
      .catch(console.error);
  }, [selectedHrid]);

  const handleSelectSkill = (skillName: string) => {
    setSelectedSkill(skillName);
    api.getExperts(skillName).then(setExperts).catch(console.error);
  };

  return (
    <div className="layout">
      <aside className="sidebar">
        <h1>SkillNet</h1>
        <button>ダッシュボード</button>
        <button>プロフィール</button>
        <button>スキル検索</button>
        <button>有識者検索</button>
        <button>ネットワーク</button>
      </aside>

      <main className="main">
        <header className="header">
          <div>
            <h2>スキル可視化・社員検索システム</h2>
            <p>社員のスキル・経歴・有識者をネットワークで可視化</p>
          </div>

          <div className="search-area">
            <input
              value={selectedHrid}
              onChange={(e) => setSelectedHrid(e.target.value)}
              placeholder="社員ID 例: A001"
            />
            <button onClick={() => setSelectedHrid(selectedHrid)}>表示</button>
          </div>
        </header>

        <section className="dashboard-grid">
          <div className="main-graph-card card">
            <h3>スキルネットワーク</h3>
            <SkillGraph graph={graph} onSelectSkill={handleSelectSkill} />
          </div>

          <ProfilePanel profile={profile} similarProfiles={similarProfiles} />

          <CareerPanel careers={profile?.careers ?? []} />

          <ExpertPanel selectedSkill={selectedSkill} experts={experts} />

          <PersonalGraph graph={personalGraph} />
        </section>
      </main>
    </div>
  );
}