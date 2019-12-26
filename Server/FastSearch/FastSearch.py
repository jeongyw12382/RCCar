import heapq

CLASS_PATH = "../MapGraphData/MapGraphData.txt"
MAX_VAL = float('infinity')


class FastSearch:

    def __call__(self, graph, src, dest):

        vertex_list = graph.vertex
        edge_list = graph.edge
        len_vertex = len(vertex_list)

        print('Searching The Graph...')

        distance = {vertex: MAX_VAL for vertex in vertex_list}
        prev = [-1 for i in range(len_vertex)]
        distance[src-1] = 0.0

        pq = [(0.0, src-1)]

        while len(pq) > 0:
            dist_curr, vertex_curr = heapq.heappop(pq)
            if dist_curr > distance[vertex_curr]:
                continue
            for edge in graph.search_edge(vertex_curr):
                dist = dist_curr + edge.w
                if dist < distance[edge.dest]:
                    distance[edge.dest] = dist
                    prev[edge.dest] = edge.src
                    heapq.heappush(pq, (dist, edge.dest))

        vertex_iter = dest-1
        tracking = [dest]
        while vertex_iter != -1:
            vertex_iter = prev[vertex_iter]
            if vertex_iter != -1:
                tracking.insert(0, vertex_iter + 1)

        print(tracking)


class Edge:

    def __init__(self, src, dest, w):
        self.src = src
        self.dest = dest
        self.w = w


class Graph:

    def __init__(self):
        self.vertex = set()
        self.edge = set()

    def update(self, filepath):
        lines = []
        with open(filepath, "r") as fd:
            for line in fd:
                lines.append(line.split())

        for line in lines:
            vertex0 = int(line[0])
            vertex1 = int(line[1])
            weight = float(line[2])
            self.vertex.add(vertex0 - 1)
            self.vertex.add(vertex1 - 1)
            self.edge.add(Edge(vertex0 - 1, vertex1 - 1, weight))
            self.edge.add(Edge(vertex1 - 1, vertex0 - 1, weight))

    def vertex_exist(self, vert):
        for vt in self.vertex:
            if vt == vert:
                return True
        return False

    def search_edge(self, vert):
        ret = []
        for edge in self.edge:
            if edge.src == vert:
                ret.append(edge)
        return ret

    def edge_exist(self, vert):
        return len(self.search_edge(vert)) != 0


if __name__ == "__main__":
    g = Graph()
    g.update(CLASS_PATH)
    search = FastSearch()
    search(g, 1, 14)
