function CountPicker({ count, setCount }) {
  return (
    <div className="space-y-3">
      <label className="block text-sm font-semibold text-gray-700">
        İstek Sayısı
      </label>
      <select
        value={count}
        onChange={(event) => setCount(event.target.value)}
        className="w-full px-4 py-3 bg-white border-2 border-gray-300 rounded-lg shadow-sm
                     focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 
                     hover:border-gray-400 transition-all cursor-pointer
                     text-gray-700 font-medium"
      >
        <option value="" disabled>
          Select count...
        </option>
        <option value="1">1</option>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="50">50</option>
        <option value="100">100</option>
      </select>
    </div>
  );
}

export default CountPicker;
