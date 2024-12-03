import Post from "../components/Post";
import SearchBar from "../components/SearchBar";

const Threads = () => {
  return (
    <>
      <main>
        <div className="flex">
          <div className="flex-1 w-64">
            <SearchBar />
            <Post />
          </div>
          <div className="flex-initial w-64">side</div>
        </div>
      </main>
    </>
  );
};

export default Threads;
