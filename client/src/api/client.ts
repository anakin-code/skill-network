const API_BASE_URL = 'http://localhost:8080';

async function fetchJson<T>(path: string): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`);

  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }

  return response.json();
}

export const api = {
  getSkillGraph: async () => {
    const data = await fetchJson<{
      nodes: { id: number; name: string; totalMonths: number }[];
      edges: { source: number; target: number; coMonths: number; normalizedWeight: number }[];
    }>('/api/skills/network');

    return {
      nodes: data.nodes.map((node) => ({
        id: node.id,
        label: node.name,
        totalMonths: node.totalMonths,
      })),
      edges: data.edges.map((edge) => ({
        source: edge.source,
        target: edge.target,
        weight: edge.normalizedWeight,
        coMonths: edge.coMonths,
      })),
    };
  },

  getProfile: (hrid: string) =>
    fetchJson(`/api/profiles/${hrid}`),

  getPersonalGraph: (hrid: string) =>
    fetchJson(`/api/profiles/${hrid}/skill-network`),

  getSimilarProfiles: (hrid: string, limit: number) =>
    fetchJson(`/api/profiles/${hrid}/similar?limit=${limit}`),

  getExperts: (skillName: string) =>
    fetchJson(`/api/skills/${encodeURIComponent(skillName)}/experts`),
};
