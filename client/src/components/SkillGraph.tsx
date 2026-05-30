import { useMemo } from "react";
import ReactFlow, {
  Background,
  Controls,
  MiniMap,
  type Edge,
  type Node,
} from "reactflow";
import dagre from "dagre";
import "reactflow/dist/style.css";
import type { SkillGraphResponse } from "../types/api";

type Props = {
  graph: SkillGraphResponse;
  onSelectSkill: (skillName: string) => void;
};

const EDGE_THRESHOLD = 0.5;

const NODE_WIDTH = 140;
const NODE_HEIGHT = 100;

function getNodeColor(skillName: string): string {
  const name = skillName.toLowerCase();

  if (
    name.includes("java") ||
    name.includes("spring") ||
    name.includes("postgresql") ||
    name === "sql"
  ) {
    return "#e8f0ff";
  }

  if (
    name.includes("python") ||
    name.includes("ai") ||
    name.includes("machine") ||
    name.includes("nlp") ||
    name.includes("graph") ||
    name.includes("data")
  ) {
    return "#e9fff2";
  }

  if (
    name.includes("aws") ||
    name.includes("docker") ||
    name.includes("kubernetes") ||
    name.includes("linux") ||
    name.includes("terraform") ||
    name.includes("ci/cd")
  ) {
    return "#fff4e6";
  }

  if (
    name.includes("project") ||
    name.includes("business") ||
    name.includes("requirement")
  ) {
    return "#f3e8ff";
  }

  return "#eef3ff";
}

function getNodeBorderColor(skillName: string): string {
  const name = skillName.toLowerCase();

  if (
    name.includes("java") ||
    name.includes("spring") ||
    name.includes("postgresql") ||
    name === "sql"
  ) {
    return "#6b8cff";
  }

  if (
    name.includes("python") ||
    name.includes("ai") ||
    name.includes("machine") ||
    name.includes("nlp") ||
    name.includes("graph") ||
    name.includes("data")
  ) {
    return "#42b883";
  }

  if (
    name.includes("aws") ||
    name.includes("docker") ||
    name.includes("kubernetes") ||
    name.includes("linux") ||
    name.includes("terraform") ||
    name.includes("ci/cd")
  ) {
    return "#f59e0b";
  }

  if (
    name.includes("project") ||
    name.includes("business") ||
    name.includes("requirement")
  ) {
    return "#a855f7";
  }

  return "#6b7280";
}

function createLayoutedNodesAndEdges(
  nodes: Node[],
  edges: Edge[]
): {
  nodes: Node[];
  edges: Edge[];
} {
  const dagreGraph = new dagre.graphlib.Graph();

  dagreGraph.setDefaultEdgeLabel(() => ({}));

  dagreGraph.setGraph({
    rankdir: "LR",
    nodesep: 80,
    ranksep: 140,
  });

  nodes.forEach((node) => {
    dagreGraph.setNode(node.id, {
      width: NODE_WIDTH,
      height: NODE_HEIGHT,
    });
  });

  edges.forEach((edge) => {
    dagreGraph.setEdge(edge.source, edge.target);
  });

  dagre.layout(dagreGraph);

  const layoutedNodes = nodes.map((node) => {
    const position = dagreGraph.node(node.id);

    return {
      ...node,
      position: {
        x: position.x - NODE_WIDTH / 2,
        y: position.y - NODE_HEIGHT / 2,
      },
    };
  });

  return {
    nodes: layoutedNodes,
    edges,
  };
}

export default function SkillGraph({
  graph,
  onSelectSkill,
}: Props) {
  const filteredEdges = useMemo(() => {
    return graph.edges.filter(
      (edge) => edge.weight >= EDGE_THRESHOLD
    );
  }, [graph.edges]);

  const visibleNodeIds = useMemo(() => {
    const ids = new Set<string>();

    filteredEdges.forEach((edge) => {
      ids.add(String(edge.source));
      ids.add(String(edge.target));
    });

    return ids;
  }, [filteredEdges]);

  const rawNodes: Node[] = useMemo(() => {
    return graph.nodes
      .filter((node) => visibleNodeIds.has(String(node.id)))
      .map((node) => {
        const size =
          90 + Math.min(55, Math.sqrt(node.totalMonths) * 3);

        return {
          id: String(node.id),
          position: {
            x: 0,
            y: 0,
          },
          data: {
            label: `${node.label}\n${node.totalMonths}ヶ月`,
            skillName: node.label,
          },
          style: {
            width: size,
            height: size,
            borderRadius: "50%",
            background: getNodeColor(node.label),
            border: `2px solid ${getNodeBorderColor(node.label)}`,
            color: "#1f2a44",
            fontWeight: 700,
            whiteSpace: "pre-line",
            textAlign: "center",
            fontSize: 11,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          },
        };
      });
  }, [graph.nodes, visibleNodeIds]);

  const rawEdges: Edge[] = useMemo(() => {
    return filteredEdges.map((edge) => ({
      id: `${edge.source}-${edge.target}`,
      source: String(edge.source),
      target: String(edge.target),
      animated: edge.weight >= 0.75,
      style: {
        strokeWidth: 1 + edge.weight * 6,
        stroke: "#9ca3af",
      },
    }));
  }, [filteredEdges]);

  const { nodes, edges } = useMemo(() => {
    return createLayoutedNodesAndEdges(rawNodes, rawEdges);
  }, [rawNodes, rawEdges]);

  return (
    <div style={{ width: "100%", height: "620px" }}>
      <div className="skill-network-legend">
        <span className="legend-item">
          <span className="legend-dot java-dot" />
          Java / DB
        </span>
        <span className="legend-item">
          <span className="legend-dot ai-dot" />
          AI / Data
        </span>
        <span className="legend-item">
          <span className="legend-dot cloud-dot" />
          Cloud / Infra
        </span>
        <span className="legend-item">
          <span className="legend-dot business-dot" />
          PM / Business
        </span>
      </div>

      <ReactFlow
        nodes={nodes}
        edges={edges}
        fitView
        fitViewOptions={{
          padding: 0.25,
        }}
        minZoom={0.2}
        maxZoom={1.8}
        onNodeClick={(_, node) =>
          onSelectSkill(String(node.data.skillName))
        }
      >
        <Background />
        <Controls />
        <MiniMap />
      </ReactFlow>
    </div>
  );
}