<template>
    <el-dialog :visible.sync="visible" :before-close="handleClose" :close-on-click-modal="false"
        :close-on-press-escape="false">
        <template #title>
            <span style="font-weight: bold; font-size: 18px; color: #333;"><i class="el-icon-edit"></i>&nbsp;&nbsp;{{
                !dataForm.id ? '添加' : '修改' }}</span>
        </template>
        <el-form v-if="!pdfUrl" :model="dataForm" :rules="dataRule" ref="dataForm"
            @keyup.enter.native="dataFormSubmitHandle()">
            <el-form-item prop="rawText" label="天气信息" label-position="top">
                <el-input type="textarea" :rows="12" placeholder="请输入内容" v-model="dataForm.rawText">
                </el-input>
            </el-form-item>
        </el-form>
        <template slot="footer">
            <el-button @click="handleClose()">取消</el-button>
            <el-button type="success" @click="dataFormSubmitHandle()">保存</el-button>
        </template>
        <div>
            <el-button v-if="!pdfUrl" type="success" @click="checkPdf">预览</el-button>
            <el-button v-if="pdfUrl" type="success" @click="updateWeather">修改天气信息</el-button>
            <iframe v-if="pdfUrl" :src="pdfUrl" width="100%" height="600px"></iframe>
        </div>
    </el-dialog>
</template>

<script>
import debounce from 'lodash/debounce';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { generateWeatherHtml } from '@/utils/weatherPdfTemplate'
import { updateWith } from 'lodash';
export default {
    data() {
        return {
            parsedData: [], // 解析后的结构化数据
            pdfUrl: null, // PDF 预览链接
            visible: false,
            saveFlag: false,
            blob: null,
            dataForm: {
                id: '',
                rawText: '',
                month: '',
                date: '',
                hours: ''
            }
        }
    },
    computed: {
        dataRule() {
            return {
                rawText: [
                    { required: true, message: "天气信息不能为空", trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            const lines = value.trim().split(/\r?\n/);
                            const errors = [];

                            lines.forEach((line, idx) => {
                                const trimmed = line.trim();
                                if (!trimmed) return; // ✅ 空行跳过当前循环，继续处理下一行

                                // 允许像“今天夜间到明天白天：”这样的段标题
                                const isTitleOnly = /^[\u4e00-\u9fa5\d\-~：：\s]+[:：]?$/.test(trimmed);
                                if (isTitleOnly) return;

                                if (!trimmed.includes('：')) {
                                    errors.push(`第 ${idx + 1} 行错误`);
                                }
                            });

                            if (errors.length) {
                                callback(new Error(errors.join('\n')));
                            } else {
                                callback();
                            }
                        },
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods: {
        init() {
            this.visible = true
            this.pdfUrl = null;
            this.parsedData = [];
            this.$nextTick(() => {
                this.$refs['dataForm'].resetFields()
                Promise.all([]).then(() => {
                    if (this.dataForm.id) {
                        this.getDetail();
                    }
                });
            })
        },
        checkPdf() {
            this.$refs['dataForm'].validate((valid) => {
                if (!valid) {
                    return false
                }
                this.generatePDF();
            })
        },
        async generatePDF() {
            const rawText = this.dataForm.rawText.trim();
            if (!rawText) {
                this.$message.warning('请输入天气信息');
                return;
            }
            let pubDate = "";
            if (this.dataForm.id) {
                pubDate = this.dataForm.month + '月' + this.dataForm.date + '日' + this.dataForm.hours + '时';
            } else {
                pubDate = await this.formatDate();
            }

            const htmlContent = generateWeatherHtml(
                rawText,
                '沾化天气预报',
                '滨州市沾化区气象局',
                pubDate + '发布'
            );

            const container = document.createElement('div');
            container.innerHTML = htmlContent;
            container.style.width = '800px';
            container.style.padding = '10px';
            container.style.position = 'absolute';
            container.style.left = '-9999px';
            container.style.top = '-9999px';

            // 一定要先添加到页面中，html2canvas 才能正确渲染
            document.body.appendChild(container);

            try {
                const canvas = await html2canvas(container, { scale: 2 });
                const imgData = canvas.toDataURL('image/png');

                const pdf = new jsPDF('p', 'mm', 'a4');
                const imgProps = pdf.getImageProperties(imgData);
                const pdfWidth = pdf.internal.pageSize.getWidth();
                const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

                pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);

                const blob = pdf.output('blob');
                this.blob = blob;
                console.log(this.saveFlag)
                if (!this.saveFlag) {
                    this.pdfUrl = URL.createObjectURL(blob);
                } else {
                    this.pdfUrl = null;
                }
            } catch (e) {
                console.error('生成PDF失败', e);
                this.$message.error('生成PDF失败');
            } finally {
                document.body.removeChild(container);
            }
        },
        updateWeather() {
            this.pdfUrl = null;
            this.parsedData = [];
        },
        getDetail() {
            this.$http.get(`/weather/forecast/detail/${this.dataForm.id}`).then(({ data: res }) => {
                if (res.code !== 0) {
                    return this.$message.error(res.msg)
                }
                this.dataForm = {
                    ...this.dataForm,
                    ...res.data
                }
            }).catch(() => { })
        },
        async formatDate() {
            try {
                const { data: res } = await this.$http.get('/weather/forecast/getdate');
                if (res.code !== 0) {
                    this.$message.error(res.msg);
                    return '';
                }
                const m = res.data.month;
                const d = res.data.date;
                const h = res.data.hours;
                return `${m}月${d}日${h}时`;
            } catch (e) {
                return '';
            }
        },
        handleClose() {
            this.pdfUrl = null;
            this.parsedData = [];
            this.dataForm = this.getDefaultForm();
            this.saveFlag = false;
            this.blob = null;
            this.visible = false;
        },
        getDefaultForm() {
            return {
                id: '',
                rawText: ''
            }
        },
        async directSubmit() {
            this.saveFlag = true;

            if (!this.blob) {
                await this.generatePDF(); // 等待 PDF 生成完成
            }

            const formData = new FormData();
            if (this.dataForm.id) {
                formData.append('id', this.dataForm.id);
            }
            formData.append('file', this.blob, 'weather.pdf');

            //BASE64转换保证传入数据包含回车符
            formData.append('rawText', btoa(unescape(encodeURIComponent(this.dataForm.rawText))));

            this.$http.post(!this.dataForm.id ? '/weather/forecast/save' : '/weather/forecast/update', formData).then(({ data: res }) => {
                if (res.code !== 0) {
                    return this.$message.error(res.msg);
                }
                this.$message({
                    message: "操作成功",
                    type: 'success',
                    duration: 500,
                    onClose: () => {
                        this.visible = false;
                        this.saveFlag = false;
                        this.$emit('refreshDataList');
                    }
                });
            }).catch(() => { });
        },
        // 表单提交
        dataFormSubmitHandle: debounce(function () {
            // 如果 dataForm 表单未挂载，则直接执行后续逻辑（适用于 PDF 模式下只生成不校验）
            if (!this.$refs['dataForm']) {
                this.directSubmit();
                return;
            }

            this.$refs['dataForm'].validate((valid) => {
                if (!valid) {
                    return false;
                }
                this.directSubmit();
            });
        }, 1000, { 'leading': true, 'trailing': false }),
    }
}
</script>
