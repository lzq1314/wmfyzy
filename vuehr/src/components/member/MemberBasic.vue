<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过员工名搜索员工,记得回车哦..."
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchMember"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchMember">搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <el-button size="mini" type="success" :loading="fileUploadBtnText=='正在导入'"><i class="fa fa-lg fa-level-up"
                                                                                          style="margin-right: 5px"></i>{{fileUploadBtnText}}
            </el-button>
          </el-upload>
          <el-button type="success" size="mini" @click="exportEmps"><i class="fa fa-lg fa-level-down"
                                                                       style="margin-right: 5px"></i>导出数据
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddEmpView">
            添加员工
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div
              style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff"
              v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="5">
                  手机号:
                  <el-input prefix-icon="el-icon-edit" v-model="params.phoneNumber" size="mini" style="width: 150px"
                            placeholder="请输入手机号"></el-input>
                </el-col>
                <el-col :span="11">
                  创建日期:
                  <el-date-picker
                    v-model="params.beginDateScope"
                    unlink-panels
                    size="mini"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="4" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchMember">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="members"
            v-loading="tableLoading"
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              align="left"
              width="30">
            </el-table-column>
            <el-table-column
              prop="name"
              align="left"
              fixed
              label="姓名"
              width="90">
            </el-table-column>
            <el-table-column
              prop="sex"
              width="80"
              align="left"
              label="性别">
            </el-table-column>
            <el-table-column
              width="150"
              align="left"
              label="创建日期">
              <template slot-scope="scope">{{ scope.row.createDate | formatDateTime}}</template>
            </el-table-column>
            <el-table-column
              prop="phoneNumber"
              width="150"
              align="left"
              label="手机号">
            </el-table-column>
            <el-table-column
              prop="telPhoneNumber"
              width="150"
              label="电话号">
            </el-table-column>
            <el-table-column
              width="150"
              prop="idCardNumber"
              label="身份证号">
            </el-table-column>
            <el-table-column
              prop="memberCardNumber"
              width="100"
              label="会员卡号">
            </el-table-column>
            <el-table-column
              prop="balance"
              label="余额">
            </el-table-column>
            <el-table-column
              prop="remark"
              width="180"
              align="left"
              label="备注">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditMemberView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button @click="addMoneyView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">充值
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           size="mini">查看高级资料
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteEmp(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="emps.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyEmps">批量删除
            </el-button>
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-form :model="member" :rules="rules" ref="addMemberForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="77%">
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="会员名称:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="member.name" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="性别:" prop="sex">
                  <el-radio-group v-model="member.sex">
                    <el-radio label="男">男</el-radio>
                    <el-radio style="margin-left: 15px" label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="手机号:" prop="phoneNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="member.phoneNumber" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="电话号:" prop="telPhoneNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="member.telPhoneNumber" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="身份证号:" prop="idCardNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="member.idCardNumber" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="会员卡号:" prop="memberCardNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="member.memberCardNumber" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="备注:" prop="remark">
                  <el-input prefix-icon="el-icon-edit" v-model="member.remark" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addMember('addMemberForm')">确 定</el-button>
  </span>
        </el-dialog>
      </div>
    </el-form>

    <el-form :model="memberMoney" :rules="rules" label-width="90px" ref="addMemberMoney">
        <div style="text-align: center">
          <el-dialog
            :title="addMoneyDialogTitle"
            style="padding: 0px;"
            :close-on-click-modal="false"
            :visible.sync="addMoneyDialogVisible"
            width="40%">
                  <el-form-item label="会员名称:" prop="name">
                    <el-input prefix-icon="el-icon-edit" v-model="memberMoney.name" size="mini" style="width: 150px"
                              placeholder="请输入员工姓名"></el-input>
                  </el-form-item>
                  <el-form-item label="手机号:" prop="phoneNumber">
                    <el-input prefix-icon="el-icon-edit" v-model="memberMoney.phoneNumber" size="mini" style="width: 150px"
                              placeholder="请输入员工姓名"></el-input>
                  </el-form-item>
                  <el-form-item label="会员卡号:" prop="memberCardNumber">
                    <el-input prefix-icon="el-icon-edit" v-model="memberMoney.memberCardNumber" size="mini" style="width: 150px"
                              placeholder="请输入员工姓名"></el-input>
                  </el-form-item>
                  <el-form-item label="充值金额:" prop="amount ">
                    <el-input prefix-icon="el-icon-edit" v-model="memberMoney.amount" size="mini" style="width: 150px"
                              placeholder="请输入员工姓名"></el-input>
                  </el-form-item>
            <span slot="footer" class="dialog-footer">
              <el-button size="mini" @click="cancelMemberMoneyEidt">取 消</el-button>
              <el-button size="mini" type="primary" @click="addMemberMoney('addMemberMoney')">确 定</el-button>
            </span>
          </el-dialog>
        </div>
      </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        emps: [],
        members: [],
        keywords: '',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        nations: [],
        politics: [],
        positions: [],
        joblevels: [],
        totalCount: -1,
        currentPage: 1,
        degrees: [{id: 4, name: '大专'}, {id: 5, name: '本科'}, {id: 6, name: '硕士'}, {id: 7, name: '博士'}, {
          id: 3,
          name: '高中'
        }, {id: 2, name: '初中'}, {id: 1, name: '小学'}, {id: 8, name: '其他'}],
        deps: [],
        defaultProps: {
          label: 'name',
          isLeaf: 'leaf',
          children: 'children'
        },
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        emp: {
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: '',
          nativePlace: '',
          politicId: '',
          email: '',
          phone: '',
          address: '',
          departmentId: '',
          departmentName: '所属部门...',
          jobLevelId: '',
          posId: '',
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: '',
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          sex: '',
          phoneNumber: '',
          telPhoneNumber: '',
          idCardNumber: '',
          memberCardNumber: '',
          remark:''
        },
        member: {
          id: '',
          sex: '',
          phoneNumber: '',
          telPhoneNumber: '',
          idCardNumber: '',
          memberCardNumber: '',
          remark:''
        },
        params:{
          phoneNumber: '',
          beginDateScope:''
        },
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}]
        },
        addMoneyDialogTitle: '',
        addMoneyDialogVisible: false,
        memberMoney: {
          id: '',
          name: '',
          phoneNumber: '',
          memberCardNumber: '',
          amount: ''
        }
      };
    },
    mounted: function () {
      this.initData();
      this.loadMembers();
    },
    methods: {
      fileUploadSuccess(response, file, fileList) {
        if (response) {
          this.$message({type: response.status, message: response.msg});
        }
        this.loadMembers();
        this.fileUploadBtnText = '导入数据';
      },
      fileUploadError(err, file, fileList) {
        this.$message({type: 'error', message: "导入失败!"});
        this.fileUploadBtnText = '导入数据';
      },
      beforeFileUpload(file) {
        this.fileUploadBtnText = '正在导入';
      },
      exportEmps() {
        window.open("/employee/basic/exportEmp", "_parent");
      },
      cancelSearch() {
        this.advanceSearchViewVisible = false;
        this.emptyMemberData();
        this.beginDateScope = '';
        this.loadMembers();
      },
      showAdvanceSearchView() {
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.keywords = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyMemberData();
          this.beginDateScope = '';
          this.loadMembers();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      deleteManyEmps() {
        this.$confirm('此操作将删除[' + this.multipleSelection.length + ']条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = '';
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        }).catch(() => {
        });
      },
      deleteEmp(row) {
        this.$confirm('此操作将永久删除[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete(row.id);
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.deleteRequest("/employee/basic/emp/" + ids).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;

            _this.loadMembers();
          }
        })
      },
      keywordsChange(val) {
        if (val == '') {
          this.loadMembers();
        }
      },
      searchMember() {
        this.loadMembers();
      },
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadMembers();
      },
      loadMembers() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/member/basic/getMemberList?page=" + this.currentPage + "&size=10&keywords="+ this.keywords+"&beginDateScope="+this.params.beginDateScope+"&phoneNumber="+this.params.phoneNumber).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.members = data.members;
            _this.totalCount = data.count;
            _this.emptyMemberData();
          }
        })
      },
      addMember(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.member.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/member/basic/updateEmp", this.member).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyMemberData();
                  _this.loadMembers();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.postRequest("/member/basic/addMember", _this.member).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyMemberData();
                  _this.loadMembers();
                }
              })
            }
          } else {
            return false;
          }
        });
      },
      addMemberMoney(addMoney){//充值
      var _this = this;
        this.tableLoading = true;
        this.postRequest("/member/basic/addMoney?id="+this.memberMoney.id+"&amount="+this.memberMoney.amount).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.addMoneyDialogVisible = false;
            _this.emptyAddMoneyData();
            _this.loadMembers();
          }
        })
      },
      cancelEidt(){
        this.dialogVisible = false;
        this.emptyMemberData();
      },
      cancelMemberMoneyEidt() {
        this.addMoneyDialogVisible= false,
        this.emptyAddMoneyData();
      },
      showDepTree() {
        this.showOrHidePop = !this.showOrHidePop;
      },
      showDepTree2() {
        this.showOrHidePop2 = !this.showOrHidePop2;
      },
      handleNodeClick(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop = false;
        this.depTextColor = '#606266';
      },
      handleNodeClick2(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop2 = false;
        this.depTextColor = '#606266';
      },
      initData() {
        var _this = this;
        this.getRequest("/employee/basic/basicdata").then(resp => {
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.nations = data.nations;
            _this.politics = data.politics;
            _this.deps = data.deps;
            _this.positions = data.positions;
            _this.joblevels = data.joblevels;
            _this.emp.workID = data.workID;
          }
        })
      },
      showEditMemberView(row) {
        this.dialogTitle = "编辑会员信息";
        this.member = row;
        this.dialogVisible = true;
      },
      addMoneyView(row){
        this.addMoneyDialogTitle = "添加员工";
        this.addMoneyDialogVisible = true;
        this.memberMoney.id = row.id;
        this.memberMoney.name = row.name;
        this.memberMoney.phoneNumber = row.phoneNumber;
        this.memberMoney.memberCardNumber = row.memberCardNumber;
      },
      showAddEmpView() {
        this.dialogTitle = "添加员工";
        this.dialogVisible = true;
        var _this = this;
        this.getRequest("/employee/basic/maxWorkID").then(resp => {
          if (resp && resp.status == 200) {
            _this.emp.workID = resp.data;
          }
        })
      },
      emptyMemberData() {
        this.member = {
          id: '',
          sex: '',
          phoneNumber: '',
          telPhoneNumber: '',
          idCardNumber: '',
          memberCardNumber: '',
          remark:''
        }
      },
      emptyAddMoneyData(){
        this.memberMoney = {
          id: '',
          name: '',
          phoneNumber: '',
          memberCardNumber: '',
          amount: ''
        }
      }
    }
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }
</style>
