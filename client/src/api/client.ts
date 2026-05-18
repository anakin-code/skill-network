import type {
  ExpertResponse,
  PersonalSkillGraphResponse,
  ProfileResponse,
  SimilarProfileResponse,
  SkillGraphResponse,
} from "../types/api";

const API_BASE = "http://localhost:8080";

async function fetchJson<T>(url: string): Promise<T> {
  const res = await fetch(`${API_BASE}${url}`);

  if (!res.ok) {
    throw new Error(`API error: ${res.status}`);
  }

  return res.json();
}

export const api = {
  getSkillGraph: () =>
    fetchJson<SkillGraphResponse>("/api/skill-network/graph"),

  getProfile: (hrid: string) =>
    fetchJson<ProfileResponse[]>(`/api/user/all`).then((profiles) =>
      profiles.find((p) => p.hrid === hrid)
    ),

  getPersonalGraph: (hrid: string) =>
    fetchJson<PersonalSkillGraphResponse>(`/api/skill-network/profile/${hrid}`),

  getExperts: (skillName: string) =>
    fetchJson<ExpertResponse[]>(
      `/api/skill-network/experts?skillName=${encodeURIComponent(skillName)}`
    ),

  getSimilarProfiles: (hrid: string, limit = 5) =>
    fetchJson<SimilarProfileResponse[]>(
      `/api/skill-network/profile/${hrid}/similar?limit=${limit}`
    ),
};